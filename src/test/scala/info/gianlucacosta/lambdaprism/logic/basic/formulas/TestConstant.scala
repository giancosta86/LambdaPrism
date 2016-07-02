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

class TestConstant extends FlatSpec with Matchers {
  "Constant" should "be equal to another having its same name" in {
    Constant("a") should be(Constant("a"))
  }


  "Constant" should "NOT be equal to another having a different name" in {
    Constant("a") should not be Constant("b")
  }


  "Constant name" should "NOT be uppercase" in {
    intercept[IllegalArgumentException] {
      Constant("A")
    }
  }

  "Constant name" should "NOT be empty" in {
    intercept[IllegalArgumentException] {
      Constant("")
    }
  }


  "Constant name" should "NOT be null" in {
    intercept[IllegalArgumentException] {
      Constant(null)
    }
  }


  "Arity" should "be zero" in {
    Constant("a").arity should be(0)
  }


  "Arguments" should "be empty" in {
    Constant("a").arguments should be(empty)
  }


  "The variables set" should "be empty" in {
    Constant("a").variables should be(empty)
  }


  "Replacing a variable" should "return the constant itself" in {
    Constant("a").replaceVariable(Variable("A"), Constant("b")) should be(Constant("a"))
  }


  "A constant" should "be ground" in {
    Constant("a").isGround should be(true)
  }
}
