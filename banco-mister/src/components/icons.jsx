const base = { fill: 'none', stroke: 'currentColor', strokeWidth: 1.3, strokeLinecap: 'round', strokeLinejoin: 'round', 'aria-hidden': true }
export const ArrowLeft = ({ size = 16 }) => <svg width={size} height={size} viewBox="0 0 24 24" {...base}><path d="M19 12H5M11 6l-6 6 6 6" /></svg>
export const ArrowRight = ({ size = 22 }) => <svg width={size} height={size} viewBox="0 0 24 24" {...base}><path d="M5 12h14M13 6l6 6-6 6" /></svg>
export const Check = ({ size = 12 }) => <svg width={size} height={size} viewBox="0 0 24 24" {...base} strokeWidth={2}><path d="M5 12.5l4.5 4.5L19 7" /></svg>
export const Mail = ({ size = 18 }) => <svg width={size} height={size} viewBox="0 0 24 24" {...base}><rect x="3" y="5" width="18" height="14" rx="1.5" /><path d="M3.5 6.5L12 13l8.5-6.5" /></svg>
export const Lock = ({ size = 20 }) => <svg width={size} height={size} viewBox="0 0 24 24" {...base}><rect x="5" y="10.5" width="14" height="9.5" rx="1.5" /><path d="M8.5 10.5V8a3.5 3.5 0 017 0v2.5" /><circle cx="12" cy="15.2" r=".6" fill="currentColor" /></svg>
export const Eye = ({ size = 18 }) => <svg width={size} height={size} viewBox="0 0 24 24" {...base}><path d="M2 12s3.6-6.5 10-6.5S22 12 22 12s-3.6 6.5-10 6.5S2 12 2 12z" /><circle cx="12" cy="12" r="2.8" /></svg>
export const EyeOff = ({ size = 18 }) => <svg width={size} height={size} viewBox="0 0 24 24" {...base}><path d="M3 3l18 18M10.6 6a9.6 9.6 0 011.4-.1c6.4 0 10 6.1 10 6.1a17 17 0 01-3.2 3.9M6.5 7.7A16.6 16.6 0 002 12s3.6 6.5 10 6.5c1.5 0 2.8-.3 4-.8M9.9 9.9a3 3 0 004.2 4.2" /></svg>
export const Shield = ({ size = 14 }) => <svg width={size} height={size} viewBox="0 0 24 24" {...base}><path d="M12 3l7 2.5v6c0 4.5-3 8-7 9.5-4-1.5-7-5-7-9.5v-6L12 3z" /></svg>
export const Help = ({ size = 16 }) => <svg width={size} height={size} viewBox="0 0 24 24" {...base}><circle cx="12" cy="12" r="9" /><path d="M9.5 9.5a2.5 2.5 0 114 2c-.9.6-1.5 1-1.5 2M12 17v.1" /></svg>
