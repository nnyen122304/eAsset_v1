mkdir "%~dp0log"
cd "%~dp0log"
start /b cmd /c Smtp4dev.exe
start /b cmd /c mockservicerunner.bat -m eAppMockService "..\eAppMock-soapui-project.xml"
