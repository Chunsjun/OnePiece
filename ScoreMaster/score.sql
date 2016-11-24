create table score (
    name varchar2(20) constraint score_name_pk PRIMARY KEY ,
    kor number(3) constraint score_kor_nn not null,
    eng number(3) constraint score_eng_nn not null,
    mat number(3) constraint score_mat_nn not null,
    tot number(3) constraint score_to_nn not null,
    ave number(3) constraint score_ave_nn not null
)
