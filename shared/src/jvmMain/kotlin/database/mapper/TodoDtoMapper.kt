package database.mapper

import database.dto.TodoDto
import org.jdbi.v3.core.mapper.RowMapper
import org.jdbi.v3.core.statement.StatementContext
import utils.fromDbToBool
import utils.toLocalDateTime
import java.sql.ResultSet

class TodoDtoMapper: RowMapper<TodoDto> {

    override fun map(rs: ResultSet, ctx: StatementContext?): TodoDto {
        return TodoDto(
            rs.getString("id"),
            rs.getString("summary"),
            rs.getString("description"),
            rs.getString("start_at").toLocalDateTime(),
            rs.getString("end_at").toLocalDateTime(),
            rs.getString("cat_id"),
            rs.getString("created_at").toLocalDateTime()!!,
            rs.getString("updated_at").toLocalDateTime()!!,
            rs.getLong("checked").fromDbToBool()
        )
    }
}