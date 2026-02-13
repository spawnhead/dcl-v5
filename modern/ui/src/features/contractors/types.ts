/**
 * Types for Contractors list. CONTRACTS: docs/screens/contractors/CONTRACTS.md.
 */

export interface LookupItemDto {
  id: string;
  name: string;
}

export interface ContractorLookupsResponse {
  defaults: {
    ctrName: string;
    ctrFullName: string;
    ctrAccount: string;
    ctrAddress: string;
    ctrEmail: string;
    ctrUnp: string;
    user: LookupItemDto | null;
    department: LookupItemDto | null;
  };
  lookups: {
    users: LookupItemDto[];
    departments: LookupItemDto[];
  };
  canCreate: boolean;
}

export interface ContractorDataRequest {
  ctrName?: string | null;
  ctrFullName?: string | null;
  ctrAccount?: string | null;
  ctrAddress?: string | null;
  ctrEmail?: string | null;
  ctrUnp?: string | null;
  user?: LookupItemDto | null;
  department?: LookupItemDto | null;
  page?: number;
  pageSize?: number;
}

export interface ContractorRowDto {
  ctrId: string;
  ctrName: string;
  ctrFullName: string;
  ctrAddress: string;
  ctrPhone: string;
  ctrFax: string;
  ctrEmail: string;
  ctrBankProps: string;
  ctrBlock: string;
  occupied: boolean;
}

export interface ContractorDataResponse {
  items: ContractorRowDto[];
  page: number;
  pageSize: number;
  hasNextPage: boolean;
}

export interface ContractorPageRequestDto {
  direction: string;
  currentPage: number;
  filterState: ContractorDataRequest;
}
