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

package info.gianlucacosta.lambdaprism.planning.problem.dialog

import info.gianlucacosta.helios.fx.dialogs.Alerts
import info.gianlucacosta.lambdaprism.planning.problem.parser.ProblemParser
import info.gianlucacosta.lambdaprism.planning.problem.parser.exceptions.ParsingException
import info.gianlucacosta.lambdaprism.planning.problem.{ExampleProblem, Problem, ProblemValidator}

import scalafx.Includes._
import scalafx.event.ActionEvent
import scalafx.scene.control.Alert.AlertType
import scalafx.scene.control.{Alert, Button, ButtonType, DialogEvent}


object ProblemDialog {
  /**
    * Utility function modally showing a ProblemDialog with the given initial problem and validator
    *
    * @param initialProblemOption
    * @param validator
    * @return
    */
  def askForProblem(
                     initialProblemOption: Option[Problem],
                     validator: ProblemValidator
                   ): Option[Problem] = {
    val problemDialog = new ProblemDialog(initialProblemOption, validator)

    problemDialog.showAndWait()

    problemDialog.stopStyling()

    problemDialog.problem
  }
}


/**
  * ScalaFX dialog where the user can input the textual definition of a planning problem.
  *
  * When the dialog is visible, if the user clicks OK:
  *
  * <ol>
  * <li>If there are syntax errors, a warning is shown</li>
  * <li>If the provided validator throws <i>IllegalArgumentException</i>, a warning in shown</li>
  * <li>Otherwise, the dialog is closed, and the problem can be accessed via the <i>problem</i> property</li>
  * </ol>
  *
  * If the user cancels the dialog, its <i>problem</i> property is set to None
  *
  * A few notes:
  *
  * <ul>
  * <li>If <i>initialProblemOption</i> is None, an example problem is shown</li>
  * <li>If two or more validators are to be applied, consider using <i>CompositeValidator</i></li>
  * <li>Before forgetting the dialog, <b>always</b> call its <i>stopStyling()</i> method</li>
  * </ul>
  *
  * @param initialProblemOption
  * @param validator
  */
class ProblemDialog(
                     initialProblemOption: Option[Problem],
                     validator: ProblemValidator
                   ) extends Alert(AlertType.Confirmation) {
  val editingExistingProblem =
    initialProblemOption.nonEmpty

  val initialProblem =
    initialProblemOption.getOrElse(ExampleProblem)


  private var _problem: Option[Problem] =
    None

  def problem: Option[Problem] =
    _problem

  private def problem_=(newValue: Option[Problem]): Unit =
    _problem = newValue


  val problemEditor = new ProblemEditor {
    setFocusTraversable(true)
    setText(initialProblem.toLanguageString)
  }

  dialogPane().setContent(problemEditor)

  onShown = (event: DialogEvent) => {
    problemEditor.requestFocus()
  }


  def stopStyling(): Unit = {
    problemEditor.stopStyling()
  }


  private val okButton: Button =
    dialogPane().lookupButton(ButtonType.OK).asInstanceOf[javafx.scene.control.Button]


  okButton.filterEvent(ActionEvent.Action) {
    event: ActionEvent => {
      try {
        val parsedProblem =
          ProblemParser.parse(problemEditor.getText)

        validator(parsedProblem)

        problem = Some(parsedProblem)
      } catch {
        case ex: IllegalArgumentException =>
          Alerts.showException(ex, alertType = AlertType.Warning)
          event.consume()

        case ex: ParsingException =>
          Alerts.showException(ex, alertType = AlertType.Warning)
          event.consume()
      }

    }
  }


  dialogPane().setPrefWidth(850)
  dialogPane().setPrefHeight(550)

  dialogPane().getStylesheets.add(
    getClass.getResource("language.css").toExternalForm
  )

  resizable = true

  title =
    if (editingExistingProblem)
      "Edit problem..."
    else
      "New problem..."


  headerText =
    "Please, enter a description of your planning problem:"
}
