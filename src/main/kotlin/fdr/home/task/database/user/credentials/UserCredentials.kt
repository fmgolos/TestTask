package fdr.home.task.database.user.credentials

import org.springframework.jdbc.core.RowMapper
import java.sql.ResultSet

data class UserCredentials(val id: Int, val login: String, val password: String) {
}

data class UserCredentialsRequest(val login: String, val password: String)

class UserCredentialsMapper : RowMapper<UserCredentials> {
    override fun mapRow(rs: ResultSet, rowNum: Int): UserCredentials {
        return UserCredentials(
            id = rs.getInt(1),
            login = rs.getString(2),
            password = rs.getString(3)
        )
    }

}