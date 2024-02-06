package viewmodel

import handler.ConfigHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope
import themes.ColorSchemeSelection
import viewmodel.state.AppConfigState
import viewmodel.state.ConfigName
import viewmodel.state.ThemeType

class AppConfigViewModel(
    private val configHandler: ConfigHandler
) : ViewModel() {

    private val _uiState = MutableStateFlow(AppConfigState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val configs = configHandler.findAllConfig().await()
            var colorScheme: ColorSchemeSelection = ColorSchemeSelection.NIGHT
            var themeType: ThemeType = ThemeType.SYSTEM

            configs.forEach { item ->
                when (item.key) {
                    ConfigName.COLOR_SCHEME -> colorScheme =
                        ColorSchemeSelection.valueOf(item.value)

                    ConfigName.THEME_TYPE -> themeType = ThemeType.valueOf(item.value)
                }
            }

            val initConfig = AppConfigState(
                selectedColorScheme = colorScheme,
                themeType = themeType
            )

            _uiState.update { initConfig }
        }
    }

    /**
     * Updating theme type in database and change the state of theme type
     */
    suspend fun updateThemeType(type: ThemeType) {
        configHandler.updateConfig(ConfigName.THEME_TYPE, type.name)
        updateThemeTypeUiState(type)
    }

    private fun updateThemeTypeUiState(type: ThemeType) {
        _uiState.update {
            it.copy(
                themeType = type
            )
        }
    }

    /**
     * Updating color scheme in database and change the state of color scheme
     */
    suspend fun updateColorScheme(colorScheme: ColorSchemeSelection) {
        configHandler.updateConfig(ConfigName.COLOR_SCHEME, colorScheme.name)
        updateColorSchemeUiState(colorScheme)
    }

    private fun updateColorSchemeUiState(colorSchemeSelected: ColorSchemeSelection) {
        _uiState.update {
            it.copy(
                selectedColorScheme = colorSchemeSelected
            )
        }
    }
}