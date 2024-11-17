ALTER TABLE public.Users ALTER COLUMN id SET DEFAULT gen_random_uuid();
ALTER TABLE public.User_roles ALTER COLUMN id SET DEFAULT gen_random_uuid();