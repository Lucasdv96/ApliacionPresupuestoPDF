$gradleWrapperUrl = "https://services.gradle.org/distributions/gradle-8.5-wrapper.zip"
$outputPath = "$PSScriptRoot\gradle-wrapper.zip"

Write-Host "Descargando Gradle wrapper..."
Invoke-WebRequest -Uri $gradleWrapperUrl -OutFile $outputPath

Write-Host "Extrayendo archivos..."
Expand-Archive -Path $outputPath -DestinationPath $PSScriptRoot -Force

Write-Host "Limpiando archivos temporales..."
Remove-Item $outputPath -Force

Write-Host "✓ Gradle wrapper configurado correctamente"
