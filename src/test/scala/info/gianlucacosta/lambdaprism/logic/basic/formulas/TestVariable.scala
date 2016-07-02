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

class TestVariable extends FlatSpec with Matchers {
  "A variable" should "be equal to another with the same name" in {
    Variable("A") should be(Variable("A"))
  }


  "A variable" should "NOT be equal to another with a different name" in {
    Variable("A") should not be Variable("B")
  }


  "Variable name" should "NOT be lowercase" in {
    intercept[IllegalArgumentException] {
      Variable("a")
    }
  }


  "Variable name" should "NOT be empty" in {
    intercept[IllegalArgumentException] {
      Variable("")
    }
  }

  "Variable name" should "NOT be null" in {
    intercept[IllegalArgumentException] {
      Variable(null)
    }
  }

  "The variables set" should "contain the variable itself" in {
    Variable("A").variables should be(Set(Variable("A")))
  }

  "Replacing another variable" should "return the variable itself" in {
    Variable("A").replaceVariable(Variable("B"), Constant("b")) should be(Variable("A"))
  }

  "Replacing the variable itself with another variable" should "return the new variable" in {
    Variable("A").replaceVariable(Variable("A"), Variable("C")) should be(Variable("C"))
  }

  "Replacing the variable itself with a constant" should "return the constant" in {
    Variable("A").replaceVariable(Variable("A"), Constant("c")) should be(Constant("c"))
  }


  "A variable" should "NOT be ground" in {
    Variable("A").isGround should not be true
  }
}
