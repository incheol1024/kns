package com.devworker.kms.repo.common;

import org.jooq.*;
import org.jooq.generated.kms.tables.KmsBoard;
import org.jooq.types.UInteger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

@Repository
public class BoardRepoImpl {
    @Autowired
    DSLContext context;

    private KmsBoard table = KmsBoard.KMS_BOARD;

    public SelectJoinStep select(TableField...fields) {
        TableField[] defaultField = new TableField[]{table.BOARD_ID, table.SUBJECT, table.USER_ID, table.REG_DATE, table.UPD_DATE, table.HITS};
        if (fields == null || fields.length == 0)
            return context.select(defaultField).from(table);
        List<TableField> list = new ArrayList<>(Arrays.asList(fields));
        list.addAll(Arrays.asList(defaultField));
        return context.select(list).from(table);
    }
}