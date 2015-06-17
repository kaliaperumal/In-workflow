@echo off
cls
echo.

echo %cd%
REM SET MY_PATH="P:\\XSLT"

java -jar support\Sequence_Number.jar

java -cp "%cd%\support\saxon9.jar" net.sf.saxon.Transform -o:"main.html" "main_id.xml" "%cd%\support\XML2HTML.xsl"
pause
