package database.mapper

import database.dto.ConfigDto
import database.dto.ConfigField
import org.jdbi.v3.core.mapper.RowMapper
import org.jdbi.v3.core.statement.StatementContext
import java.sql.ResultSet

class ConfigDtoMapper: RowMapper<ConfigDto> {
    override fun map(rs: ResultSet, ctx: StatementContext?): ConfigDto {
        return ConfigDto(
            key = rs.getString(ConfigField.KEY),
            value = rs.getString(ConfigField.VALUE)
        )
    }
}