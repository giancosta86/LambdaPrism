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

import info.gianlucacosta.lambdaprism.logic.basic.formulas.{Functor, Variable}
import info.gianlucacosta.lambdaprism.planning.problem.ProblemLanguage
import info.gianlucacosta.omnieditor.StyledCodeEditor

/**
  * Editor dedicated to the definition of a planning problem
  */
class ProblemEditor extends StyledCodeEditor {
  setWrapText(true)

  getStyleClass.add("problemEditor")

  addPattern("comment", ProblemLanguage.CommentPattern)
  addTokens("keyword", ProblemLanguage.Keywords: _*)

  addPattern("constantName", Functor.ValidNamePattern.pattern())
  addPattern("variableName", Variable.ValidNamePattern.pattern())
}
