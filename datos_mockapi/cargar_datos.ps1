# Carga las 3 categorías y los 7 productos propios en SU proyecto de MockAPI.io.
# Uso (PowerShell):  .\cargar_datos.ps1 -BaseUrl "https://xxxxxxxx.mockapi.io/api/v1"
# Antes borre los datos falsos que MockAPI genera al crear cada recurso.
# Los otros 3 productos (importados) se crean desde la app: Catálogo -> tocar un producto.
param([Parameter(Mandatory = $true)][string]$BaseUrl)

$BaseUrl = $BaseUrl.TrimEnd('/')
$carpeta = Split-Path -Parent $MyInvocation.MyCommand.Path

foreach ($recurso in @('categoria', 'producto')) {
    $items = Get-Content -Raw -Encoding UTF8 (Join-Path $carpeta "$recurso.json") | ConvertFrom-Json
    foreach ($item in $items) {
        $cuerpo = [System.Text.Encoding]::UTF8.GetBytes(($item | ConvertTo-Json))
        Invoke-RestMethod -Method Post -Uri "$BaseUrl/$recurso" -ContentType 'application/json; charset=utf-8' -Body $cuerpo | Out-Null
        Write-Host "POST $recurso -> $($item.nombre)"
    }
}
Write-Host 'Listo.'
