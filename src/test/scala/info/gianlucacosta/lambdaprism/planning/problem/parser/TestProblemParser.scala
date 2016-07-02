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

package info.gianlucacosta.lambdaprism.planning.problem.parser

import java.io.InputStreamReader

import info.gianlucacosta.lambdaprism.planning.problem.ExampleProblem
import org.scalatest.{FlatSpec, Matchers}

class TestProblemParser extends FlatSpec with Matchers {
  "The example problem" should "be correctly parsed from its definition in a file" in {
    val problemReader =
      new InputStreamReader(getClass.getResourceAsStream("ExampleProblem.txt"))

    try {
      val parsedProblem =
        ProblemParser.parse(problemReader)

      parsedProblem should be(ExampleProblem)
    } finally {
      problemReader.close()
    }
  }


  "The example problem" should "be correctly parsed from its string definition" in {
    val parsedProblem =
      ProblemParser.parse(ExampleProblem.toLanguageString)

    parsedProblem should be(ExampleProblem)
  }
}
