#!/usr/bin/env bash

/c/oraclexe/app/oracle/product/11.2.0/server/bin/sqlplus -s alms_ah/alms_ah@AMADEVDB03 << EOF
whenever sqlerror exit sql.sqlcode;
set echo off 
set heading off

@pl_script_1.sql

exit;
EOF
