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
  * View obtained by grouping the table rows of a ClassificationProblem
  * by the values of an input attribute.
  *
  * According to the view's input attribute, a view contains
  * <i>known items</i> (that is, items whose attribute value is known)
  * and <i>unknown items</i> (having the placeholder in lieu of a definite
  * attribute value)
  */
trait ClassificationView {
  /**
    * The classification problem generating the view
    *
    * @return
    */
  def problem: ClassificationProblem

  /**
    * The attribute on which the view is based
    *
    * @return
    */
  def attribute: Attribute

  /**
    * All the values assumed by the view's underlying attribute
    *
    * @return
    */
  def attributeValues: Set[Value]


  /**
    * Returns the # of items having the given value of the view's attribute
    * and the given value of the output attribute.
    *
    * @param attributeValue
    * @param outputValue
    * @return The # of items, or 0 if inexisting attribute values are passed
    */
  def getKnownItemsCount(attributeValue: Value)(outputValue: Value): Int


  /**
    * Returns the aggregate # of items having the given value of the view's attribute
    *
    * @param attributeValue
    * @return The # of items, or 0 if an inexisting attribute value is passed
    */
  def getKnownItemsAggregateCount(attributeValue: Value): Int


  /**
    * The total number of known items in the view
    */
  def knownItemsTotalCount: Int


  /**
    * Returns the # of unknown items (that is, items whose value
    * for the view's attribute is the unknown placeholder) having the
    * given output value.
    *
    * @param outputValue
    * @return The # of items, or 0 if a missing output value is passed
    */
  def getUnknownItemsCount(outputValue: Value): Int


  /**
    * The total number of unknown items in the view
    */
  def unknownItemsTotalCount: Int


  /**
    * The entropy of the known items in the view
    */
  val entropy: Double


  /**
    * The information gain for the view
    */
  val gain: Double


  /**
    * Creates a decision tree out of the view's data
    *
    * @return a DecisionTree
    */
  def createDecisionTree(): DecisionTree
}
