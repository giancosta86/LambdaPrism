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

package info.gianlucacosta.lambdaprism.logic.basic.matching

import info.gianlucacosta.lambdaprism.logic.basic.formulas.{Constant, Variable}
import org.scalatest.{FlatSpec, Matchers}


class TestBindingGroup extends FlatSpec with Matchers {
  "An empty variable set" should "raise an exception" in {
    intercept[IllegalArgumentException] {
      BindingGroup(variables = Set(), ground = None)
    }
  }


  "toString for free variable A" should "return A" in {
    BindingGroup(
      variables = Set(
        Variable("A")
      ),

      ground = None
    ).toString should be("A")
  }


  "toString for A bound to 4" should "return A = 4" in {
    BindingGroup(
      variables = Set(
        Variable("A")
      ),

      ground = Some(Constant("4"))
    ).toString should be("A = 4")
  }


  "toString for free B and A" should "return A = B" in {
    BindingGroup(
      variables = Set(
        Variable("B"),
        Variable("A")
      ),

      ground = None
    ).toString should be("A = B")
  }


  "toString for B and A bound to 5" should "return A = B = 5" in {
    BindingGroup(
      variables = Set(
        Variable("B"),
        Variable("A")
      ),

      ground = Some(Constant("5"))
    ).toString should be("A = B = 5")
  }


  "toString for free B, C and A" should "return A = B = C" in {
    BindingGroup(
      variables = Set(
        Variable("B"),
        Variable("C"),
        Variable("A")
      ),

      ground = None
    ).toString should be("A = B = C")
  }


  "toString for B, C and A bound to 7" should "return A = B = C = 7" in {
    BindingGroup(
      variables = Set(
        Variable("B"),
        Variable("C"),
        Variable("A")
      ),

      ground = Some(Constant("7"))
    ).toString should be("A = B = C = 7")
  }


  "contains" should "return false if a variable is NOT contained" in {
    BindingGroup(
      variables = Set(
        Variable("B"),
        Variable("C"),
        Variable("A")
      ),

      ground = Some(Constant("7"))
    ).contains(Variable("Z")) should be(false)
  }


  "contains" should "return true if a variable is contained" in {
    BindingGroup(
      variables = Set(
        Variable("B"),
        Variable("C"),
        Variable("A")
      ),

      ground = Some(Constant("7"))
    ).contains(Variable("C")) should be(true)
  }
}
