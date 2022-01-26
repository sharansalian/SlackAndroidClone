package dev.baseio.slackclone.navigator

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class Screen(
  private val baseRoute: String,
  val navArguments: List<NamedNavArgument> = emptyList()
) {
  val route: String = baseRoute.appendArguments(navArguments)

  // onboarding
  object GettingStarted : Screen("gettingStarted")
  object SkipTypingScreen : Screen("SkipTypingUI")
  object EmailAddressInputUI : Screen("EmailAddressInputUI")
  object WorkspaceInputUI: Screen("WorkspaceInputUI")

  // auth
  object Auth : Screen("auth")
  object ForgotPassword : Screen("forgotPassword")

  // dashboard
  object Dashboard:Screen("Dashboard")

}

private fun String.appendArguments(navArguments: List<NamedNavArgument>): String {
  val mandatoryArguments = navArguments.filter { it.argument.defaultValue == null }
    .takeIf { it.isNotEmpty() }
    ?.joinToString(separator = "/", prefix = "/") { "{${it.name}}" }
    .orEmpty()
  val optionalArguments = navArguments.filter { it.argument.defaultValue != null }
    .takeIf { it.isNotEmpty() }
    ?.joinToString(separator = "&", prefix = "?") { "${it.name}={${it.name}}" }
    .orEmpty()
  return "$this$mandatoryArguments$optionalArguments"
}