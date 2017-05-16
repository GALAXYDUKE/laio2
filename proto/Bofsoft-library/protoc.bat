@echo off
for /f "tokens=*" %%i in ('dir /s/b ".\*.proto"') do protoc.exe --java_out ./ %%~nxi
pause