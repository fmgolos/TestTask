package fdr.home.task.database.user.credentials

import org.springframework.jdbc.core.RowMapper
import java.sql.ResultSet

data class UserCredentials(val login: String, val password: String) {
}

class UserCredentialsMapper : RowMapper<UserCredentials> {
    override fun mapRow(rs: ResultSet, rowNum: Int): UserCredentials {
        return UserCredentials(
            login = rs.getString(1),
            password = rs.getString(2)
        )
    }

}