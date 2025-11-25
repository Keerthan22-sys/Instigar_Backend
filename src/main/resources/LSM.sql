create table public.lead (
                             id bigint primary key not null,
                             assigned_to character varying(255),
                             course character varying(255),
                             created_at timestamp(6) without time zone default now(),
                             created_by character varying(255),
                             date_added character varying(255),
                             email character varying(255),
                             name character varying(255) not null,
                             notes text,
                             phone character varying(255),
                             source character varying(255),
                             stage character varying(255),
                             type text default false,
                             updated_at timestamp(6) without time zone default now(),
                             updated_by character varying(255)
);

create table public.users (
                              id bigint primary key not null,
                              created_at timestamp(6) without time zone default now(),
                              password character varying(255) not null,
                              role character varying(255) not null,
                              username character varying(255) not null
);
create unique index ukr43af9ap4edm43mmtq01oddj6 on users using btree (username);


INSERT INTO public.lead (
    id,
    assigned_to,
    course,
    created_at,
    created_by,
    date_added,
    email,
    name,
    notes,
    phone,
    source,
    stage,
    updated_at,
    updated_by
) VALUES (
    1,
    'John Doe',
    'Introduction to Programming',
    '2023-10-01 12:00:00',
    'admin',
    '2023-10-01',
    'johndoe@example.com',
    'John Doe',
    'Interested in learning programming basics.',
    '+1234567890',
    'Website',
    'New Lead',
    '2023-10-01 12:00:00',
    'admin'
);


