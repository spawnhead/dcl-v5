/**
 * Orders list (N2). CONTRACTS: docs/screens/orders/CONTRACTS.md.
 */

export interface OrderRowDto {
  ord_id: number;
  ord_number: string;
  ord_date: string | null;
  ord_contractor: string;
  ord_contractor_for: string;
  ord_summ: number;
  ord_date_conf: string | null;
  ord_sent_to_prod_date?: string | null;
  ord_received_conf_date?: string | null;
  ord_conf_sent_date?: string | null;
  ord_ready_for_deliv_date?: string | null;
  ord_ready_for_deliv: number;
  ord_executed_date?: string | null;
  ord_user: string;
  ord_department: string;
  is_warn: string;
  ord_block: string;
  ord_annul: number;
  ord_num_conf: string | null;
  dep_id: number | null;
  ord_link_to_spec: number;
  ord_comment_flag: number;
  have_empty_date_conf?: string | null;
  count_day_curr_minus_sent?: number | null;
  ord_ship_from_stock?: string | null;
  ord_arrive_in_lithuania?: string | null;
  usr_id_create?: string | null;
  can_edit_clone?: boolean | null;
  can_block?: boolean | null;
}

export interface OrderListResponse {
  items: OrderRowDto[];
  total: number;
  page: number;
  pageSize: number;
}

export interface LookupItemDto {
  id: string;
  name: string;
}

export interface OrderFilterParams {
  number?: string;
  date_begin?: string;
  date_end?: string;
  contractor_id?: string;
  contractor_for_id?: string;
  user_id?: string;
  department_id?: string;
  stuff_category_id?: string;
  contract_number?: string;
  specification_number?: string;
  seller_for_who_id?: string;
  sum_min?: number;
  sum_max?: number;
  executed?: boolean;
  not_executed?: boolean;
  ord_ready_for_deliv?: boolean;
  ord_annul_not_show?: boolean;
  state_a?: boolean;
  state_3?: boolean;
  state_b?: boolean;
  state_exclamation?: boolean;
  state_c?: boolean;
  ord_num_conf?: string;
  page: number;
  pageSize: number;
  order_by?: string;
}
