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

class TestCompoundFunctor extends FlatSpec with Matchers {

  "Compound functor" should "be equal to another which is structurally equal" in {
    CompoundFunctor(
      "f",
      List(
        Constant("a"),
        Variable("Beta"),
        Constant("c")
      )
    ) should be(
      CompoundFunctor(
        "f",
        List(
          Constant("a"),
          Variable("Beta"),
          Constant("c")
        )
      )
    )
  }

  "Compound functor" should "NOT be equal to a functor having different name" in {
    CompoundFunctor(
      "f",
      List(
        Constant("a"),
        Variable("Beta"),
        Constant("c")
      )
    ) should not be
      CompoundFunctor(
        "g",
        List(
          Constant("a"),
          Variable("Beta"),
          Constant("c")
        )
      )
  }


  "Compound functor" should "NOT be equal to a functor having different arity" in {
    CompoundFunctor(
      "f",
      List(
        Constant("a"),
        Variable("Beta"),
        Constant("c")
      )
    ) should not be
      CompoundFunctor(
        "f",
        List(
          Constant("a"),
          Variable("Beta")
        )
      )
  }

  "Compound functor name" should "NOT be uppercase" in {
    intercept[IllegalArgumentException] {
      CompoundFunctor(
        "F",
        List()
      )
    }
  }

  "Compound functor arguments" should "NOT be empty" in {
    intercept[IllegalArgumentException] {
      CompoundFunctor(
        "f",
        List()
      )
    }
  }

  "Compound functor name" should "NOT be empty" in {
    intercept[IllegalArgumentException] {
      CompoundFunctor("", List())
    }
  }

  "Compound functor name" should "NOT be null" in {
    intercept[IllegalArgumentException] {
      CompoundFunctor(null, List())
    }
  }

  "Arity" should "be the number of arguments" in {
    CompoundFunctor(
      "f",
      List(
        Constant("a"),
        Variable("Beta"),
        Constant("c")
      )
    ).arity should be(3)
  }


  "Arguments" should "be correctly retrieved" in {
    CompoundFunctor(
      "f",
      List(
        Constant("a"),
        Variable("Beta"),
        Constant("c")
      )
    ).arguments should be(
      List(
        Constant("a"),
        Variable("Beta"),
        Constant("c")
      )
    )
  }

  "The variables set" should "contain all the variables in the argument list" in {
    CompoundFunctor(
      "f",
      List(
        Constant("a"),
        Variable("Beta"),
        Variable("Gamma"),
        Constant("delta")
      )
    ).variables should be(
      Set(
        Variable("Beta"),
        Variable("Gamma")
      )
    )
  }


  "Replacing a missing variable" should "return the functor itself" in {
    CompoundFunctor(
      "f",
      List(
        Constant("a"),
        Variable("Beta"),
        Variable("Gamma"),
        Constant("delta")
      )
    ).replaceVariable(Variable("Zeta"), Constant("zeta")) should be(
      CompoundFunctor(
        "f",
        List(
          Constant("a"),
          Variable("Beta"),
          Variable("Gamma"),
          Constant("delta")
        )
      )
    )
  }

  "Replacing a variable with another variable" should "replace the variable" in {
    CompoundFunctor(
      "f",
      List(
        Constant("a"),
        Variable("Beta"),
        Variable("Gamma"),
        Constant("delta")
      )
    ).replaceVariable(Variable("Gamma"), Variable("Omega")) should be(
      CompoundFunctor(
        "f",
        List(
          Constant("a"),
          Variable("Beta"),
          Variable("Omega"),
          Constant("delta")
        )
      )
    )
  }


  "Replacing a variable having multiple instances with another variable" should "replace every instance of the variable" in {
    CompoundFunctor(
      "f",
      List(
        Constant("a"),
        Variable("Gamma"),
        Variable("Beta"),
        Variable("Gamma"),
        Constant("delta")
      )
    ).replaceVariable(Variable("Gamma"), Variable("Omega")) should be(
      CompoundFunctor(
        "f",
        List(
          Constant("a"),
          Variable("Omega"),
          Variable("Beta"),
          Variable("Omega"),
          Constant("delta")
        )
      )
    )
  }


  "Replacing a variable with a constant" should "replace the variable" in {
    CompoundFunctor(
      "f",
      List(
        Constant("a"),
        Variable("Beta"),
        Variable("Gamma"),
        Constant("delta")
      )
    ).replaceVariable(Variable("Gamma"), Constant("omega")) should be(
      CompoundFunctor(
        "f",
        List(
          Constant("a"),
          Variable("Beta"),
          Constant("omega"),
          Constant("delta")
        )
      )
    )
  }


  "Replacing a variable having multiple instances with a constant" should "replace every instance of the variable" in {
    CompoundFunctor(
      "f",
      List(
        Constant("a"),
        Variable("Gamma"),
        Variable("Beta"),
        Variable("Gamma"),
        Constant("delta")
      )
    ).replaceVariable(Variable("Gamma"), Constant("c")) should be(
      CompoundFunctor(
        "f",
        List(
          Constant("a"),
          Constant("c"),
          Variable("Beta"),
          Constant("c"),
          Constant("delta")
        )
      )
    )
  }


  "f(a, b, c)" should "be ground" in {
    CompoundFunctor(
      "f",
      List(
        Constant("a"),
        Constant("b"),
        Constant("c")
      )
    ).isGround should be(true)
  }


  "f(a, B, c)" should "NOT be ground" in {
    CompoundFunctor(
      "f",
      List(
        Constant("a"),
        Variable("B"),
        Constant("c")
      )
    ).isGround should be(false)
  }
}
