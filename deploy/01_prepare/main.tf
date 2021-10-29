provider "azurerm" {
  features {}
}

# resource groupの作成
resource "azurerm_resource_group" "rg" {
  location = var.rg_location
  name     = var.rg_name
}

# redis cache
resource "azurerm_redis_cache" "redis" {
  capacity            = 0
  family              = "C"
  location            = azurerm_resource_group.rg.location
  name                = var.redis_name
  resource_group_name = azurerm_resource_group.rg.name
  sku_name            = "Standard"
  enable_non_ssl_port = false
  minimum_tls_version = "1.2"
  redis_version = "6"
}

# Log Analytics Workspace
resource "azurerm_log_analytics_workspace" "log" {
  location            = azurerm_resource_group.rg.location
  name                = var.log_workspace_name
  resource_group_name = azurerm_resource_group.rg.name
}
