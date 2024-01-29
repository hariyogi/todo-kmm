package database.mapper

import database.dto.TodoDto
import org.jdbi.v3.core.mapper.RowMapper
import org.jdbi.v3.core.statement.StatementContext
import utils.fromDbToBool
import java.sql.ResultSet
import java.time.LocalDateTime

class TodoDtoMapper: RowMapper<TodoDto> {

    override fun map(rs: ResultSet, ctx: StatementContext?): TodoDto {
        return TodoDto(
            rs.getString("id"),
            rs.getString("summary"),
            rs.getString("description"),
            LocalDateTime.parse(rs.getString("created_at")),
            LocalDateTime.parse(rs.getString("updated_at")),
            rs.getLong("checked").fromDbToBool()
        )
    }
}