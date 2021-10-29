provider "azurerm" {
  features {}
}

data "azurerm_container_registry" "cr" {
  name                = var.acr_name
  resource_group_name = var.rg_name
}

data "azurerm_redis_cache" "redis" {
  name                = var.redis_name
  resource_group_name = var.rg_name
}

data "azurerm_log_analytics_workspace" "log" {
  name                = var.log_workspace_name
  resource_group_name = var.rg_name
}

resource "azurerm_container_group" "aci" {
  location            = var.rg_location
  name                = var.container_instance_name
  os_type             = "linux"
  resource_group_name = var.rg_name
  # IPアドレスの設定はPublicかPrivateかのいずれかであるため、とりあえず仮のものを設定
  ip_address_type = "Public"
  restart_policy  = "Never"

  image_registry_credential {
    password = data.azurerm_container_registry.cr.admin_password
    server   = data.azurerm_container_registry.cr.login_server
    username = data.azurerm_container_registry.cr.admin_username
  }

  container {
    cpu    = 0.5
    image  = "${data.azurerm_container_registry.cr.login_server}/redis-sample:latest"
    memory = 1.5
    name   = "redis-sample"
    # ポートの設定は必須っぽいので、適当なものを設定
    ports {
      port     = 443
      protocol = "TCP"
    }
    secure_environment_variables = {
      "SPRING_PROFILES_ACTIVE"    = "prod",
      "MYAPP_REDIS_HOST" = data.azurerm_redis_cache.redis.hostname
      "MYAPP_REDIS_PORT" = data.azurerm_redis_cache.redis.ssl_port
      "MYAPP_REDIS_PASSWORD" = data.azurerm_redis_cache.redis.primary_access_key
      "MYAPP_REDIS_CONNECT_TIMEOUT" = "5s"
      "MYAPP_REDIS_COMMAND_TIMEOUT" = "3s"
      "MYAPP_REDIS_CLUSTER_REFRESH_ADAPTIVE" = true
      "MYAPP_REDIS_CLUSTER_REFRESH_PERIOD" = "5s"
    }
  }

  # Log Analytics Workspaceの設定
  diagnostics {
    log_analytics {
      workspace_id  = data.azurerm_log_analytics_workspace.log.workspace_id
      workspace_key = data.azurerm_log_analytics_workspace.log.primary_shared_key
    }
  }
}
