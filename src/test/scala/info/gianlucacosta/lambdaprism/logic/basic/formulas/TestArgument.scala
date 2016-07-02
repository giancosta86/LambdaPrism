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

package info.gianlucacosta.lambdaprism.logic.basic.formulas

import org.scalatest.{FlatSpec, Matchers}

class TestArgument extends FlatSpec with Matchers {
  "Parsing a lowercase token" should "return a constant" in {
    Argument.parse("alpha") should be(Constant("alpha"))
  }


  "Parsing an uppercase token" should "return a variable" in {
    Argument.parse("Alpha") should be(Variable("Alpha"))
  }


  "Parsing a null token" should "throw an exception" in {
    intercept[IllegalArgumentException] {
      Argument.parse(null)
    }
  }


  "Parsing an empty token" should "throw an exception" in {
    intercept[IllegalArgumentException] {
      Argument.parse("")
    }
  }


  "Parsing a token with an invalid char" should "throw an exception" in {
    intercept[IllegalArgumentException] {
      Argument.parse("axv$ty")
    }
  }
}
