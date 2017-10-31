#!/usr/bin/env bash

sqlplus -s alms_ah/alms_ah@AMADEVDB03 << EOF
whenever sqlerror exit sql.sqlcode;
set echo off 
set heading off

@pl_script_1.sql

exit;
EOF
