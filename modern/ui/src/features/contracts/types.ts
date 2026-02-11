/**
 * Types for Contracts list (N3). CONTRACTS: docs/screens/contracts/CONTRACTS.md.
 */

export interface LookupItemDto {
  id: string;
  name: string;
}

export interface DefaultsDto {
  number: string;
  dateBegin: string;
  dateEnd: string;
  sumMin: number | null;
  sumMax: number | null;
  executed: boolean;
  notExecuted: boolean;
  oridinalAbsent: boolean;
  contractor: LookupItemDto | null;
  user: LookupItemDto | null;
  seller: LookupItemDto | null;
}

export interface ContractsLookupsResponse {
  defaults: DefaultsDto;
  lookups: {
    contractors: LookupItemDto[];
    users: LookupItemDto[];
    sellers: LookupItemDto[];
  };
}

export interface ContractDataRequest {
  number?: string | null;
  contractor?: LookupItemDto | null;
  dateBegin?: string | null;
  dateEnd?: string | null;
  sumMin?: number | null;
  sumMax?: number | null;
  user?: LookupItemDto | null;
  seller?: LookupItemDto | null;
  executed?: boolean | null;
  notExecuted?: boolean | null;
  oridinalAbsent?: boolean | null;
  page?: number;
  pageSize?: number;
}

export interface ContractRowDto {
  conId: string;
  conNumber: string;
  conDate: string;
  conContractor: string;
  conSumm: string;
  conCurrency: string;
  notes: string;
  conExecuted: string;
  conUser: string;
  conReminder: string;
  conAnnul: string;
  attachIdx: number;
  spcCount: number;
  usrIdList: string;
  depIdList: string;
}

export interface SortItemDto {
  field: string;
  direction: string;
}

export interface ContractDataResponse {
  items: ContractRowDto[];
  page: number;
  pageSize: number;
  hasNextPage: boolean;
  sort: SortItemDto[];
}

export interface PageRequestDto {
  direction: 'next' | 'prev';
  currentPage: number;
  filterState: ContractDataRequest | null;
}

export interface CleanAllResponse {
  defaults: DefaultsDto;
  grid: ContractDataResponse;
}
