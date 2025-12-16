Write-Host "Cleaning Java Language Server Cache..." -ForegroundColor Cyan

$paths = @(
    "$env:APPDATA\Code\User\workspaceStorage"
)

foreach ($basePath in $paths) {
    if (Test-Path $basePath) {
        Write-Host "Searching in: $basePath" -ForegroundColor Yellow
        
        Get-ChildItem -Path $basePath -Directory | ForEach-Object {
            $javaDir = Join-Path $_.FullName "redhat.java"
            if (Test-Path $javaDir) {
                try {
                    Remove-Item -Path $javaDir -Recurse -Force -ErrorAction Stop
                    Write-Host "  Removed: $javaDir" -ForegroundColor Green
                }
                catch {
                    Write-Host "  Failed to remove: $javaDir" -ForegroundColor Red
                }
            }
        }
    }
}

Write-Host "`nCache cleanup complete!" -ForegroundColor Green
Write-Host "Please restart VS Code for changes to take effect." -ForegroundColor Cyan
