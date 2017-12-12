package com.unico.dao;

import com.unico.json.PairParameterMapper;
import com.unico.json.PairParameters;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import java.util.List;

@RegisterMapper(PairParameterMapper.class)
public interface GcdDao {
    @SqlQuery("select * from PARAMETERS order by ts asc")
    public List<PairParameters> getAllParameters();

    @SqlUpdate("insert into PARAMETERS values(:a, :b, CURRENT_TIMESTAMP)")
    public void insertParams(@Bind("a") int a,@Bind("b") int b);

    @SqlQuery("select calculated from GCD order by ts asc")
    public List<Integer> getCalculatedGCD();


    @SqlUpdate("insert into GCD values (:calculated, CURRENT_TIMESTAMP)")
    public void insertCalculatedGcd(@Bind("calculated") int calculated);


}
