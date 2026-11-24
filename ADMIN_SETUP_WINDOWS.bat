@echo off
cd /d "%~dp0"
if not exist "%~dp0pom.xml" (echo ERROR: Open the full ParaQ_Next project folder containing pom.xml. & pause & exit /b 1)
where java >nul 2>nul || (echo Java 21 is required & pause & exit /b 1)
where mvn >nul 2>nul || (echo Apache Maven 3.9+ is required & pause & exit /b 1)
where powershell >nul 2>nul || (echo Windows PowerShell is required & pause & exit /b 1)
powershell -NoProfile -ExecutionPolicy Bypass -File "%~dp0ADMIN_SETUP_WINDOWS.ps1"
if errorlevel 1 echo Admin setup stopped with an error. Check the messages above.
pause
