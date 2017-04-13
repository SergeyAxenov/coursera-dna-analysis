:: ===============================================================
:: Runs compiled jar piping file provided as a parameter to stdin
:: Usage:
:: for https://stepik.org/lesson/Overview-9293/step/9
:: dna.cmd src/test/resources/code00-example-sampleInput1.txt
:: dna.cmd src/test/resources/code00-example-sampleInput2.txt
:: ===============================================================
@echo off
IF %1.==. GOTO NoInput

SET mypath=%~dp0
SET dnajar=%mypath%\target\scala-2.11\dna-analysis-assembly-1.0.jar

if not exist %dnajar% GOTO NoJar

java -jar "%dnajar%" <  %1
GOTO End1

:NoJar
  ECHO %dnajar% does not exist. Run sbt assembly.
GOTO End1

:NoInput
  ECHO Expected input file is not provided
GOTO End1

:End1