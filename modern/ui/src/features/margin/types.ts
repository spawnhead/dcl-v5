/**
 * Margin API types (mirror backend DTOs until generate:api adds them).
 * CONTRACTS: docs/screens/margin/CONTRACTS.md, grid-fetch.response.json
 */
export interface MarginLineDto {
  ctr_name: string;
  cut_name: string;
  con_number_formatted: string;
  con_date_formatted: string;
  spc_number_formatted: string;
  spc_date_formatted: string;
  spc_summ_formatted: string;
  cur_name: string;
  stf_name_show: string;
  shp_number_show: string;
  shp_date_show: string;
  pay_date_show: string;
  lps_summ_eur_formatted: string;
  lps_summ_formatted: string;
  lps_sum_transport_formatted: string;
  lcc_transport_formatted: string;
  lps_custom_formatted: string;
  lcc_charges_formatted: string;
  lcc_montage_formatted: string;
  lps_montage_time_formatted: string;
  montage_cost_formatted: string;
  lcc_update_sum_formatted: string;
  summ_formatted: string;
  summ_zak_formatted: string;
  margin_formatted: string;
  koeff_formatted: string;
  usr_name_show: string;
  dep_name_show: string;
  itogLine: boolean;
  spc_group_delivery: string;
  haveUnblockedPrc: boolean;
}

export interface ViewFlagsDto {
  view_contractor: boolean;
  view_country: boolean;
  view_contract: boolean;
  view_stuff_category: boolean;
  view_shipping: boolean;
  view_payment: boolean;
  view_transport: boolean;
  view_transport_sum: boolean;
  view_custom: boolean;
  view_other_sum: boolean;
  view_montage_sum: boolean;
  view_montage_time: boolean;
  view_montage_cost: boolean;
  view_update_sum: boolean;
  view_summ_zak: boolean;
  view_koeff: boolean;
  view_user: boolean;
  view_department: boolean;
}

export interface MarginMetaDto {
  rowsTotal: number;
  rowsReturned: number;
  limited: boolean;
}

export interface MarginGridResponse {
  data: MarginLineDto[];
  view: ViewFlagsDto;
  meta: MarginMetaDto;
}

export interface LookupItemResponse {
  id: string;
  name: string;
}

export interface MarginGenerateRequest {
  date_begin?: string;
  date_end?: string;
  user?: { id: string; name: string };
  department?: { id: string; name: string };
  contractor?: { id: string; name: string };
  stuffCategory?: { id: string; name: string };
  route?: { id: string; name: string };
  user_aspect?: boolean;
  department_aspect?: boolean;
  contractor_aspect?: boolean;
  stuff_category_aspect?: boolean;
  route_aspect?: boolean;
  onlyTotal?: boolean;
  itog_by_spec?: boolean;
  itog_by_user?: boolean;
  itog_by_product?: boolean;
  get_not_block?: boolean;
  view?: Record<string, boolean>;
}
