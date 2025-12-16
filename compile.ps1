Write-Host "Compiling Smart Library Management System..." -ForegroundColor Cyan

if (Test-Path "target") {
    Remove-Item -Path "target\*" -Recurse -Force
    Write-Host "Cleaned target directory" -ForegroundColor Green
}

New-Item -ItemType Directory -Path "target" -Force | Out-Null

Write-Host "Compiling domain classes..." -ForegroundColor Yellow
javac -d target -sourcepath src src\domain\*.java
if ($LASTEXITCODE -eq 0) {
    Write-Host "Domain classes compiled successfully" -ForegroundColor Green
}
else {
    Write-Host "Domain compilation failed" -ForegroundColor Red
    exit 1
}

Write-Host "Compiling pattern classes..." -ForegroundColor Yellow
Get-ChildItem -Path src\patterns -Recurse -Filter *.java | ForEach-Object {
    javac -d target -cp target -sourcepath src $_.FullName
}
if ($LASTEXITCODE -eq 0) {
    Write-Host "Pattern classes compiled successfully" -ForegroundColor Green
}
else {
    Write-Host "Pattern compilation failed" -ForegroundColor Red
    exit 1
}

Write-Host "Compiling system classes..." -ForegroundColor Yellow
javac -d target -cp target -sourcepath src src\system\*.java
if ($LASTEXITCODE -eq 0) {
    Write-Host "System classes compiled successfully" -ForegroundColor Green
}
else {
    Write-Host "System compilation failed" -ForegroundColor Red
    exit 1
}

Write-Host "Compiling main class..." -ForegroundColor Yellow
if (Test-Path "src\Main.java") {
    javac -d target -cp target -sourcepath src src\Main.java
    if ($LASTEXITCODE -eq 0) {
        Write-Host "Main class compiled successfully" -ForegroundColor Green
    }
}

Write-Host "Compiling interactive main class..." -ForegroundColor Yellow
if (Test-Path "src\InteractiveMain.java") {
    javac -d target -cp target -sourcepath src src\InteractiveMain.java
    if ($LASTEXITCODE -eq 0) {
        Write-Host "InteractiveMain class compiled successfully" -ForegroundColor Green
    }
}

Write-Host "All classes compiled successfully!" -ForegroundColor Green
