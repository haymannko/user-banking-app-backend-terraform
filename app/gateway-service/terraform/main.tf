provider "aws" {
  region = var.aws_region
}

resource "aws_ecr_repository" "service_repo" {
  name = var.service_name
  image_tag_mutability = "MUTABLE"

  image_scanning_configuration {
    scan_on_push = true
  }
}

resource "aws_iam_role" "apprunner_ecr_role" {
  name = "${var.service_name}-apprunner-ecr-role"

  assume_role_policy = jsonencode({
    Version = "2012-10-17"
    Statement = [
      {
        Action = "sts:AssumeRole"
        Effect = "Allow"
        Principal = {
          Service = "build.apprunner.amazonaws.com"
        }
      }
    ]
  })
}

resource "aws_iam_role_policy_attachment" "apprunner_ecr_policy" {
  role       = aws_iam_role.apprunner_ecr_role.name
  policy_arn = "arn:aws:iam::aws:policy/service-role/AWSAppRunnerServicePolicyForECRAccess"
}

resource "aws_apprunner_service" "service" {
  service_name = var.service_name
  
  source_configuration {
    authentication_configuration {
      access_role_arn = aws_iam_role.apprunner_ecr_role.arn
    }
    image_repository {
      image_identifier      = "${aws_ecr_repository.service_repo.repository_url}:latest"
      image_repository_type = "ECR"
      image_configuration {
        port = "8080"
      }
    }
    auto_deployments_enabled = true
  }

  tags = {
    Name = var.service_name
  }
}
