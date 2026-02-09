package net.sam.dcl.beans;

import java.io.Serializable;
import java.lang.*;

/**
 * Created by IntelliJ IDEA.
 * Date: 31.08.2005
 * Time: 18:53:07
 * To change this template use File | Settings | File Templates.
 */
public class Constants implements Serializable
{
	final static public String replacementFromString = "from";

	final static public String localIpPrefix = "192.168.0.";
	final static public String localMachinePrefix = "0:0:0:0:0:0:0:1";

	final static public String commercialProposalBlankType = "1";
	final static public String orderBlankType = "2";
	final static public String commonBlankType = "3";
	final static public String commonLightBlankType = "4";
	final static public String letterRequestBlankType = "5";

	final static public String letterHeadNoticeForShippingName = "ui.letterHeadNoticeForShipping";
	final static public String contractorWhereNoticeForShipping = "ui.contractorWhereNoticeForShipping";
	final static public String letterRequestBlank = "ui.letterRequestBlank";
	final static public String commonBlankLight1 = "ui.commonBlankLight1";
	final static public String commonBlankLight2 = "ui.commonBlankLight2";
	final static public String commonBlankLight3 = "ui.commonBlankLight3";
	final static public String blankSmerkonaActPrint = "blankSmerkonaActPrint";
	final static public String blankKropaActPrint = "blankKropaActPrint";
	final static public String blankLBTActPrint = "blankLBTActPrint";
	final static public String blankLATActPrint = "blankLATActPrint";
	final static public String defaultPurposeProduce = "defaultPurposeProduce";
	final static public String defaultPurposeFairTrade = "defaultPurposeFairTrade";
	final static public String defaultPurposeGuaranteeRepair = "defaultPurposeGuaranteeRepair";
	final static public String lengthNumber1C = "lengthNumber1C";
	final static public String maxReputationLevel = "maxReputationLevel";
	final static public String defaultNomenclatureBlank = "defaultNomenclatureBlank";
	final static public String defaultNomenclatureUnit = "defaultNomenclatureUnit";
	final static public String attachmentsDir = "attachmentsDir";
	final static public String importResultsDir = "importResultsDir";
	final static public String nomenclatureProduceMainLang = "nomenclatureProduceMainLang";
	final static public String defaultGuarantyServiceActBlank = "defaultGuarantyServiceActBlank";
	final static public String defaultCPInvoiceBlank = "defaultCPInvoiceBlank";
	final static public String defaultCPCurrency = "defaultCPCurrency";
	final static public String defaultCPTableCurrency = "defaultCPTableCurrency";
	final static public String contractorLinteraRequestPrint = "contractorLinteraRequestPrint";
	final static public String contractorLTS = "contractorLTS";
	final static public String blankLinteraRequestPrint = "blankLinteraRequestPrint";
	final static public String blankSmerkonaRequestPrint = "blankSmerkonaRequestPrint";
	final static public String blankKropaRequestPrint = "blankKropaRequestPrint";
	final static public String blankLBTRequestPrint = "blankLBTRequestPrint";
	final static public String blankLATRequestPrint = "blankLATRequestPrint";

	final static public String dayCountDeductShippings = "dayCountDeductShippings";
	final static public String dayCountDeductShippingPositions = "dayCountDeductShippingPositions";
	final static public String dayCountWaitReservedForShipping = "dayCountWaitReservedForShipping";

	final static public String dayCountDeductPayments = "dayCountDeductPayments";
	final static public String dayCountDeductCommercialProposals = "dayCountDeductCommercialProposals";
	final static public String dayCountDeductConditionsForContract = "dayCountDeductConditionsForContract";
	final static public String dayCountDeductDeliveryRequests = "dayCountDeductDeliveryRequests";

	final static public String routeProducesForAssembleMinsk = "routeProducesForAssembleMinsk";
	final static public String runEveryDayTaskCronScheduler = "runEveryDayTaskCronScheduler";
	final static public String drpPriceCoefficient = "drpPriceCoefficient";
	final static public String cpToContractImportAsmMinskSeller = "cpToContractImportAsmMinskSeller";
	final static public String minCourseCoefficient = "minCourseCoefficient";
	final static public String defaultSpcAdditionalDaysCount = "defaultSpcAdditionalDaysCount";
	final static public String deleteUserMessagesPeriod = "deleteUserMessagesPeriod";
	final static public String timeOutForNoForProduceError = "timeOutForNoForProduceError";

	final static public String userRefreshPeriod = "userRefreshPeriod";
	final static public int minUserRefreshPeriod = 1;
	final static public String userShowPaymentByAllDep = "userShowPaymentByAllDep";

	final static public String routeRBClient = "1";

	//ЗАО "Линтера"
	final static public String linteraSellerId = "0";
	//"Линтера ТехСервис"
	final static public String techServiceSellerId = "1";
}