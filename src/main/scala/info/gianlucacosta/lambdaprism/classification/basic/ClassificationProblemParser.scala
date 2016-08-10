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
  * Parser for ClassificationProblem
  */
object ClassificationProblemParser {
  /**
    * Reads a problem definition from the given string
    *
    * @param sourceString The source string. Lines are trimmed, then empty lines are ignored.
    * @return
    */
  def parse(sourceString: String): ClassificationProblem = {
    val actualLines =
      sourceString
        .split('\n')
        .map(_.trim)
        .filter(_.nonEmpty)
        .map(line =>
          line
            .split(raw"\s*,\s*")
            .toList
        )
        .toList


    require(
      actualLines.nonEmpty,
      "No attributes declared"
    )

    val attributes :: valueTable =
      actualLines

    ClassificationProblem(
      attributes,
      valueTable
    )
  }

}
