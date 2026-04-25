$gradleDir = "$PSScriptRoot\gradle\wrapper"
New-Item -ItemType Directory -Path $gradleDir -Force | Out-Null

$url = "https://raw.githubusercontent.com/gradle/gradle/master/gradle/wrapper/gradle-wrapper.jar"
$output = "$gradleDir\gradle-wrapper.jar"

Write-Host "Descargando gradle-wrapper.jar..."
try {
    [Net.ServicePointManager]::SecurityProtocol = [Net.ServicePointManager]::SecurityProtocol -bor [Net.SecurityProtocolType]::Tls12
    Invoke-WebRequest -Uri $url -OutFile $output -ErrorAction Stop
    Write-Host "✓ Archivo descargado correctamente"
} catch {
    Write-Host "✗ Error descargando: $_"
    exit 1
}
