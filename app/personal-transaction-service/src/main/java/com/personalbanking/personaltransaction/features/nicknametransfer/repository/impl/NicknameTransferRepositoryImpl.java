package com.personalbanking.personaltransaction.features.nicknametransfer.repository.impl;

import com.personalbanking.personaltransaction.features.nicknametransfer.models.AccountDetail;
import com.personalbanking.personaltransaction.features.nicknametransfer.models.Nickname;
import com.personalbanking.personaltransaction.features.nicknametransfer.models.Transaction;
import com.personalbanking.personaltransaction.features.nicknametransfer.repository.NicknameTransferRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class NicknameTransferRepositoryImpl implements NicknameTransferRepository {

    private final JdbcTemplate jdbcTemplate;

    public NicknameTransferRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Nickname> nicknameRowMapper = (rs, rowNum) ->
            new Nickname(
                    rs.getLong("id"),
                    rs.getString("nickname")
            );

    private final RowMapper<AccountDetail> accountDetailRowMapper = (rs, rowNum) ->
            new AccountDetail(
                    rs.getLong("id"),
                    rs.getLong("user_id"),
                    rs.getString("account_number"),
                    rs.getLong("account_type_id"),
                    rs.getLong("group_id"),
                    rs.getBigDecimal("current_balance"),
                    rs.getLong("role_id"),
                    rs.getLong("nickname_id"),
                    rs.getLong("kyc_id"),
                    rs.getString("created_at"),
                    rs.getString("updated_at")
            );

    @Override
    public Optional<Nickname> findNicknameById(Long nicknameId) {
        String sql = "SELECT * FROM nickname WHERE id = ?";
        return jdbcTemplate.query(sql, nicknameRowMapper, nicknameId)
                .stream()
                .findFirst();
    }

    @Override
    public Optional<AccountDetail> findAccountByNicknameId(Long nicknameId) {
        String sql = "SELECT * FROM account_detail WHERE nickname_id = ?";
        return jdbcTemplate.query(sql,accountDetailRowMapper, nicknameId)
                .stream()
                .findFirst();
    }

    @Override
    public Optional<AccountDetail> findAccountByUserId(Long userId) {
        String sql = "SELECT * FROM account_detail WHERE user_id = ?";
        return jdbcTemplate.query(sql,accountDetailRowMapper, userId)
                .stream()
                .findFirst();
    }

    @Override
    public String prepareTransfer(Long fromAccountId, Long toAccountId, Long userId) {
        return "TXN_" + java.util.UUID.randomUUID();
    }

    @Override
    public Long saveTransaction(Transaction transaction) {
        String sql = """
                INSERT INTO transactions (
                    credit_account_id,
                    debit_account_id,
                    amount,
                    status,
                    created_at,
                    updated_at)
                VALUES (?, ?, ?, ?, ?, ?)
                RETURNING id
                """;
        return jdbcTemplate.queryForObject(
                sql,
                Long.class,
                transaction.creditAccountId(),
                transaction.debitAccountId(),
                transaction.amount(),
                transaction.status(),
                transaction.createdAt(),
                transaction.updatedAt()
        );
    }
}
