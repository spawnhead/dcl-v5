package net.sam.dcl.beans;

import net.sam.dcl.dbo.DboProduce;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * Date: 14.09.2005
 * Time: 17:17:20
 * To change this template use File | Settings | File Templates.
 */
public class ProduceMovement implements Serializable
{
  DboProduce produce = new DboProduce();

  boolean showLegend;
  boolean showLegendInput;
  boolean showLegendOrder;
  boolean showLegendTransit;
  double countOrd;
  double countOrdExecuted;
  double countOrdRest;
  double restOrd;
  double countTransit;
  double countTransitRest;
  double countIn;
  double countOut;
  double rest;

  List<ProduceMovementProduct.ProduceResultListLine> resultList = new ArrayList<ProduceMovementProduct.ProduceResultListLine>();
  List<ProduceMovementProduct.ProduceResultListLine> resultListChain = new ArrayList<ProduceMovementProduct.ProduceResultListLine>();

  public ProduceMovement()
  {
  }

  public ProduceMovement(DboProduce produce)
  {
    this.produce = produce;
  }

  public ProduceMovement(ProduceMovement produceMovement)
  {
    produce = produceMovement.getProduce();
    showLegend = produceMovement.isShowLegend();
    showLegendInput = produceMovement.isShowLegendInput();
    showLegendOrder = produceMovement.isShowLegendOrder();
    showLegendTransit = produceMovement.isShowLegendTransit();
    countOrd = produceMovement.getCountOrd();
    countOrdExecuted = produceMovement.getCountOrdExecuted();
    countOrdRest = produceMovement.getCountOrdRest();
    restOrd = produceMovement.getRestOrd();
    countTransit = produceMovement.getCountTransit();
    countTransitRest = produceMovement.getCountTransitRest();
    countIn = produceMovement.getCountIn();
    countOut = produceMovement.getCountOut();
    rest = produceMovement.getRest();
    resultList = produceMovement.getResultList();
    resultListChain = produceMovement.getResultListChain();
  }

  public DboProduce getProduce()
  {
    return produce;
  }

  public void setProduce(DboProduce produce)
  {
    this.produce = produce;
  }

  public boolean isShowLegend()
  {
    return showLegend;
  }

  public void setShowLegend(boolean showLegend)
  {
    this.showLegend = showLegend;
  }

  public boolean isShowLegendInput()
  {
    return showLegendInput;
  }

  public void setShowLegendInput(boolean showLegendInput)
  {
    this.showLegendInput = showLegendInput;
  }

  public boolean isShowLegendOrder()
  {
    return showLegendOrder;
  }

  public void setShowLegendOrder(boolean showLegendOrder)
  {
    this.showLegendOrder = showLegendOrder;
  }

  public boolean isShowLegendTransit()
  {
    return showLegendTransit;
  }

  public void setShowLegendTransit(boolean showLegendTransit)
  {
    this.showLegendTransit = showLegendTransit;
  }

  public double getCountOrd()
  {
    return countOrd;
  }

  public void setCountOrd(double countOrd)
  {
    this.countOrd = countOrd;
  }

  public double getCountOrdExecuted()
  {
    return countOrdExecuted;
  }

  public void setCountOrdExecuted(double countOrdExecuted)
  {
    this.countOrdExecuted = countOrdExecuted;
  }

  public double getCountOrdRest()
  {
    return countOrdRest;
  }

  public void setCountOrdRest(double countOrdRest)
  {
    this.countOrdRest = countOrdRest;
  }

  public double getRestOrd()
  {
    return restOrd;
  }

  public void setRestOrd(double restOrd)
  {
    this.restOrd = restOrd;
  }

  public double getCountTransit()
  {
    return countTransit;
  }

  public void setCountTransit(double countTransit)
  {
    this.countTransit = countTransit;
  }

  public double getCountTransitRest()
  {
    return countTransitRest;
  }

  public void setCountTransitRest(double countTransitRest)
  {
    this.countTransitRest = countTransitRest;
  }

  public double getCountIn()
  {
    return countIn;
  }

  public void setCountIn(double countIn)
  {
    this.countIn = countIn;
  }

  public double getCountOut()
  {
    return countOut;
  }

  public void setCountOut(double countOut)
  {
    this.countOut = countOut;
  }

  public double getRest()
  {
    return rest;
  }

  public void setRest(double rest)
  {
    this.rest = rest;
  }

  public List<ProduceMovementProduct.ProduceResultListLine> getResultList()
  {
    return resultList;
  }

  public void setResultList(List<ProduceMovementProduct.ProduceResultListLine> resultList)
  {
    this.resultList = resultList;
  }

  public List<ProduceMovementProduct.ProduceResultListLine> getResultListChain()
  {
    return resultListChain;
  }

  public void setResultListChain(List<ProduceMovementProduct.ProduceResultListLine> resultListChain)
  {
    this.resultListChain = resultListChain;
  }
}
