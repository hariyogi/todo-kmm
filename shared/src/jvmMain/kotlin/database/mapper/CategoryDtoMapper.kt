package database.mapper

import database.dto.CategoryDto
import database.dto.CategoryField
import org.jdbi.v3.core.mapper.RowMapper
import org.jdbi.v3.core.statement.StatementContext
import java.sql.ResultSet
import java.time.LocalDateTime

class CategoryDtoMapper: RowMapper<CategoryDto> {
    override fun map(rs: ResultSet, ctx: StatementContext?): CategoryDto {
        return CategoryDto(
            rs.getString(CategoryField.ID),
            rs.getString(CategoryField.NAME),
            rs.getString(CategoryField.DESC),
            rs.getString(CategoryField.COLOR),
            LocalDateTime.parse(rs.getString(CategoryField.CREATED_AT)),
            LocalDateTime.parse(rs.getString(CategoryField.UPDATED_AT))
        )
    }
}