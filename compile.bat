@echo off 

set PATH_COMPILE=D:\App\JAVA\money_count
echo Compile %1 using path %PATH_COMPILE%
if not "%2"=="" echo Additional Param: client=%2

if "%1" == "all" goto compile_all

if "%1" == "lib" goto compile_lib
if "%1" == "trx" goto compile_trx

goto end

:compile_all
cd %PATH_COMPILE%\money-count-lib
call mvn clean install
cd %PATH_COMPILE%\money-count-trx
call mvn clean package
goto end

:compile_lib
cd %PATH_COMPILE%\money-count-lib
call mvn clean install
goto end

:compile_trx
cd %PATH_COMPILE%\money-count-trx
if "%2"=="" (call mvn clean package) else (call mvn clean package -Dclient=%2)
goto end

:end
cd %PATH_COMPILE%