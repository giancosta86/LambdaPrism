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

/**
  * Exam - 8 January 2009
  */
object ExampleProblem extends ClassificationProblem(
  List(
    "Temperature",
    "Humidity",
    "Class"
  ),

  List(
    List("High", "High", "Pos"),
    List("High", "Low", "Neg"),
    List("Low", "Medium", "Pos"),
    List("Low", "High", "Pos"),
    List("High", "Medium", "Neg"),
    List("Low", "Low", "Pos"),
    List("Low", "Low", "Neg"),
    List("?", "Low", "Neg"),
    List("High", "High", "Pos"),
    List("High", "High", "Neg"),
    List("Low", "Low", "Pos"),
    List("High", "High", "Neg"),
    List("?", "Medium", "Pos"),
    List("Low", "Medium", "Pos"),
    List("High", "Medium", "Neg"),
    List("Low", "Low", "Pos"),
    List("?", "Low", "Pos")
  )
)
