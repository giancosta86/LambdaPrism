/*§
  ===========================================================================
  LambdaPrism
  ===========================================================================
  Copyright (C) 2016 Gianluca Costa
  ===========================================================================
  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
  ===========================================================================
*/

package info.gianlucacosta.lambdaprism.classification.basic

import info.gianlucacosta.helios.fx.dialogs.{Alerts, InputDialogs}

import scalafx.scene.control.Alert.AlertType

/**
  * Dialog asking input for ClassificationProblem
  */
object ClassificationProblemDialog {
  /**
    * Asks the user for the description of a ClassificationProblem.
    *
    * @param initialProblemOption The problem initially shown; if None, an example
    *                             problem will be shown instead
    * @return A valid ClassificationProblem instance,
    *         or None if the user canceled the dialog
    */
  def askForProblem(
                     initialProblemOption: Option[ClassificationProblem]
                   ): Option[ClassificationProblem] = {

    val actualInitialProblem =
      initialProblemOption
        .getOrElse(ExampleProblem)


    val headerText =
      if (initialProblemOption.isEmpty)
        "New classification problem..."
      else
        "Edit classification problem..."


    var inputProblem: Option[ClassificationProblem] =
      None


    val dialogResult =
      InputDialogs.askForText(
        "Please, enter a description of the problem:",

        actualInitialProblem.toString,

        headerText,

        (inputText) => {
          try {
            inputProblem =
              Some(
                ClassificationProblemParser.parse(inputText)
              )
            true
          } catch {
            case ex: IllegalArgumentException =>
              Alerts.showException(ex, alertType = AlertType.Warning)
              false
          }
        },

        "-fx-font-family: monospace; -fx-font-size: 16px;"
      )


    dialogResult.flatMap(_ =>
      inputProblem
    )
  }
}

