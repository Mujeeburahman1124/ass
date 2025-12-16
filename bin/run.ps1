# Smart Library Management System - Run Script
Write-Host "`n========================================" -ForegroundColor Cyan
Write-Host "Running Library Management System..." -ForegroundColor Cyan
Write-Host "========================================`n" -ForegroundColor Cyan

# Run the Java application
java -cp target Main

Write-Host "`n========================================" -ForegroundColor Cyan
Write-Host "Application Execution Complete!" -ForegroundColor Green
Write-Host "========================================`n" -ForegroundColor Cyan

# Pause to see output
Write-Host "Press any key to continue..." -ForegroundColor Yellow
$null = $Host.UI.RawUI.ReadKey("NoEcho,IncludeKeyDown")
