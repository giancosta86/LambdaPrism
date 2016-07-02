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

import java.io.{Reader, StringReader}

import info.gianlucacosta.lambdaprism.planning.problem.Problem
import org.antlr.v4.runtime.{ANTLRInputStream, CommonTokenStream}

/**
  * Creates a planning problem after reading its definition from different sources
  */
object ProblemParser {
  /**
    * Reads a problem definition from the given reader
    *
    * @param sourceReader
    * @return
    */
  def parse(sourceReader: Reader): Problem = {
    val charStream = new ANTLRInputStream(sourceReader)
    val lexer = new PlanningProblemLexer(charStream)
    val tokenStream = new CommonTokenStream(lexer)

    val parser = new PlanningProblemParser(tokenStream)

    lexer.removeErrorListeners()
    parser.removeErrorListeners()
    parser.addErrorListener(new SyntaxErrorListener)

    val problemContext = parser.problem()

    val problemBuildingVisitor = new ProblemBuildingVisitor
    problemBuildingVisitor.visitProblem(problemContext)
  }


  /**
    * Reads a problem definition from the given string
    *
    * @param sourceString
    * @return
    */
  def parse(sourceString: String): Problem =
    parse(new StringReader(sourceString))
}
