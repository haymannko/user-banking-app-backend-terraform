variable "aws_region" {
  description = "The AWS region to deploy the service to."
  type        = string
  default     = "us-east-1"
}

variable "service_name" {
  description = "The name of the service."
  type        = string
  default     = "gateway-service"
}
