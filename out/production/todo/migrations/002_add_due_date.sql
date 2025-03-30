--
-- PostgreSQL database dump
--

-- Dumped from database version 17.2
-- Dumped by pg_dump version 17.2

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: categories; Type: TABLE; Schema: public; Owner: jack
--

CREATE TABLE public.categories (
    id integer NOT NULL,
    name character varying(50) NOT NULL
);


ALTER TABLE public.categories OWNER TO jack;

--
-- Name: categories_id_seq; Type: SEQUENCE; Schema: public; Owner: jack
--

CREATE SEQUENCE public.categories_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.categories_id_seq OWNER TO jack;

--
-- Name: categories_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: jack
--

ALTER SEQUENCE public.categories_id_seq OWNED BY public.categories.id;


--
-- Name: priorities; Type: TABLE; Schema: public; Owner: jack
--

CREATE TABLE public.priorities (
    name character varying(10) NOT NULL,
    default_interval interval NOT NULL
);


ALTER TABLE public.priorities OWNER TO jack;

--
-- Name: quotes; Type: TABLE; Schema: public; Owner: jack
--

CREATE TABLE public.quotes (
    id integer NOT NULL,
    text text NOT NULL,
    author character varying(100) DEFAULT 'anonymous'::character varying
);


ALTER TABLE public.quotes OWNER TO jack;

--
-- Name: quotes_id_seq; Type: SEQUENCE; Schema: public; Owner: jack
--

CREATE SEQUENCE public.quotes_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.quotes_id_seq OWNER TO jack;

--
-- Name: quotes_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: jack
--

ALTER SEQUENCE public.quotes_id_seq OWNED BY public.quotes.id;


--
-- Name: reminders; Type: TABLE; Schema: public; Owner: jack
--

CREATE TABLE public.reminders (
    id integer NOT NULL,
    task_id integer NOT NULL,
    reminder_time timestamp without time zone NOT NULL
);


ALTER TABLE public.reminders OWNER TO jack;

--
-- Name: reminders_id_seq; Type: SEQUENCE; Schema: public; Owner: jack
--

CREATE SEQUENCE public.reminders_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.reminders_id_seq OWNER TO jack;

--
-- Name: reminders_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: jack
--

ALTER SEQUENCE public.reminders_id_seq OWNED BY public.reminders.id;


--
-- Name: rewards; Type: TABLE; Schema: public; Owner: jack
--

CREATE TABLE public.rewards (
    id integer NOT NULL,
    reward_name character varying(50) NOT NULL
);


ALTER TABLE public.rewards OWNER TO jack;

--
-- Name: rewards_id_seq; Type: SEQUENCE; Schema: public; Owner: jack
--

CREATE SEQUENCE public.rewards_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.rewards_id_seq OWNER TO jack;

--
-- Name: rewards_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: jack
--

ALTER SEQUENCE public.rewards_id_seq OWNED BY public.rewards.id;


--
-- Name: task_categories; Type: TABLE; Schema: public; Owner: jack
--

CREATE TABLE public.task_categories (
    task_id integer NOT NULL,
    category_id integer NOT NULL
);


ALTER TABLE public.task_categories OWNER TO jack;

--
-- Name: task_log; Type: TABLE; Schema: public; Owner: jack
--

CREATE TABLE public.task_log (
    id integer NOT NULL,
    user_id integer,
    month date NOT NULL,
    category_id integer,
    unfinished_count integer DEFAULT 0 NOT NULL
);


ALTER TABLE public.task_log OWNER TO jack;

--
-- Name: task_log_id_seq; Type: SEQUENCE; Schema: public; Owner: jack
--

CREATE SEQUENCE public.task_log_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.task_log_id_seq OWNER TO jack;

--
-- Name: task_log_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: jack
--

ALTER SEQUENCE public.task_log_id_seq OWNED BY public.task_log.id;


--
-- Name: tasks; Type: TABLE; Schema: public; Owner: jack
--

CREATE TABLE public.tasks (
    id integer NOT NULL,
    title character varying(100) NOT NULL,
    description text DEFAULT ''::text,
    deadline timestamp without time zone NOT NULL,
    priority character varying(10) DEFAULT 'medium'::character varying NOT NULL,
    status character varying(15) DEFAULT 'todo'::character varying NOT NULL,
    user_id integer,
    date_issued timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    completed_at timestamp without time zone
);


ALTER TABLE public.tasks OWNER TO jack;

--
-- Name: tasks_id_seq; Type: SEQUENCE; Schema: public; Owner: jack
--

CREATE SEQUENCE public.tasks_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.tasks_id_seq OWNER TO jack;

--
-- Name: tasks_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: jack
--

ALTER SEQUENCE public.tasks_id_seq OWNED BY public.tasks.id;


--
-- Name: user_rewards; Type: TABLE; Schema: public; Owner: jack
--

CREATE TABLE public.user_rewards (
    user_id integer NOT NULL,
    reward_id integer NOT NULL,
    achieved_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP
);


ALTER TABLE public.user_rewards OWNER TO jack;

--
-- Name: users; Type: TABLE; Schema: public; Owner: jack
--

CREATE TABLE public.users (
    user_id integer NOT NULL,
    username character varying(20) NOT NULL
);


ALTER TABLE public.users OWNER TO jack;

--
-- Name: users_user_id_seq; Type: SEQUENCE; Schema: public; Owner: jack
--

CREATE SEQUENCE public.users_user_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.users_user_id_seq OWNER TO jack;

--
-- Name: users_user_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: jack
--

ALTER SEQUENCE public.users_user_id_seq OWNED BY public.users.user_id;


--
-- Name: categories id; Type: DEFAULT; Schema: public; Owner: jack
--

ALTER TABLE ONLY public.categories ALTER COLUMN id SET DEFAULT nextval('public.categories_id_seq'::regclass);


--
-- Name: quotes id; Type: DEFAULT; Schema: public; Owner: jack
--

ALTER TABLE ONLY public.quotes ALTER COLUMN id SET DEFAULT nextval('public.quotes_id_seq'::regclass);


--
-- Name: reminders id; Type: DEFAULT; Schema: public; Owner: jack
--

ALTER TABLE ONLY public.reminders ALTER COLUMN id SET DEFAULT nextval('public.reminders_id_seq'::regclass);


--
-- Name: rewards id; Type: DEFAULT; Schema: public; Owner: jack
--

ALTER TABLE ONLY public.rewards ALTER COLUMN id SET DEFAULT nextval('public.rewards_id_seq'::regclass);


--
-- Name: task_log id; Type: DEFAULT; Schema: public; Owner: jack
--

ALTER TABLE ONLY public.task_log ALTER COLUMN id SET DEFAULT nextval('public.task_log_id_seq'::regclass);


--
-- Name: tasks id; Type: DEFAULT; Schema: public; Owner: jack
--

ALTER TABLE ONLY public.tasks ALTER COLUMN id SET DEFAULT nextval('public.tasks_id_seq'::regclass);


--
-- Name: users user_id; Type: DEFAULT; Schema: public; Owner: jack
--

ALTER TABLE ONLY public.users ALTER COLUMN user_id SET DEFAULT nextval('public.users_user_id_seq'::regclass);


--
-- Name: categories categories_name_key; Type: CONSTRAINT; Schema: public; Owner: jack
--

ALTER TABLE ONLY public.categories
    ADD CONSTRAINT categories_name_key UNIQUE (name);


--
-- Name: categories categories_pkey; Type: CONSTRAINT; Schema: public; Owner: jack
--

ALTER TABLE ONLY public.categories
    ADD CONSTRAINT categories_pkey PRIMARY KEY (id);


--
-- Name: priorities priorities_pkey; Type: CONSTRAINT; Schema: public; Owner: jack
--

ALTER TABLE ONLY public.priorities
    ADD CONSTRAINT priorities_pkey PRIMARY KEY (name);


--
-- Name: quotes quotes_pkey; Type: CONSTRAINT; Schema: public; Owner: jack
--

ALTER TABLE ONLY public.quotes
    ADD CONSTRAINT quotes_pkey PRIMARY KEY (id);


--
-- Name: reminders reminders_pkey; Type: CONSTRAINT; Schema: public; Owner: jack
--

ALTER TABLE ONLY public.reminders
    ADD CONSTRAINT reminders_pkey PRIMARY KEY (id);


--
-- Name: rewards rewards_pkey; Type: CONSTRAINT; Schema: public; Owner: jack
--

ALTER TABLE ONLY public.rewards
    ADD CONSTRAINT rewards_pkey PRIMARY KEY (id);


--
-- Name: rewards rewards_reward_name_key; Type: CONSTRAINT; Schema: public; Owner: jack
--

ALTER TABLE ONLY public.rewards
    ADD CONSTRAINT rewards_reward_name_key UNIQUE (reward_name);


--
-- Name: task_categories task_categories_pkey; Type: CONSTRAINT; Schema: public; Owner: jack
--

ALTER TABLE ONLY public.task_categories
    ADD CONSTRAINT task_categories_pkey PRIMARY KEY (task_id, category_id);


--
-- Name: task_log task_log_pkey; Type: CONSTRAINT; Schema: public; Owner: jack
--

ALTER TABLE ONLY public.task_log
    ADD CONSTRAINT task_log_pkey PRIMARY KEY (id);


--
-- Name: tasks tasks_pkey; Type: CONSTRAINT; Schema: public; Owner: jack
--

ALTER TABLE ONLY public.tasks
    ADD CONSTRAINT tasks_pkey PRIMARY KEY (id);


--
-- Name: user_rewards user_rewards_pkey; Type: CONSTRAINT; Schema: public; Owner: jack
--

ALTER TABLE ONLY public.user_rewards
    ADD CONSTRAINT user_rewards_pkey PRIMARY KEY (user_id, reward_id);


--
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: jack
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (user_id);


--
-- Name: users users_username_key; Type: CONSTRAINT; Schema: public; Owner: jack
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_username_key UNIQUE (username);


--
-- Name: tasks fk_priority; Type: FK CONSTRAINT; Schema: public; Owner: jack
--

ALTER TABLE ONLY public.tasks
    ADD CONSTRAINT fk_priority FOREIGN KEY (priority) REFERENCES public.priorities(name);


--
-- Name: task_categories task_categories_category_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: jack
--

ALTER TABLE ONLY public.task_categories
    ADD CONSTRAINT task_categories_category_id_fkey FOREIGN KEY (category_id) REFERENCES public.categories(id) ON DELETE CASCADE;


--
-- Name: task_log task_log_category_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: jack
--

ALTER TABLE ONLY public.task_log
    ADD CONSTRAINT task_log_category_id_fkey FOREIGN KEY (category_id) REFERENCES public.categories(id);


--
-- Name: task_log task_log_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: jack
--

ALTER TABLE ONLY public.task_log
    ADD CONSTRAINT task_log_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.users(user_id) ON DELETE CASCADE;


--
-- Name: tasks tasks_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: jack
--

ALTER TABLE ONLY public.tasks
    ADD CONSTRAINT tasks_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.users(user_id) ON DELETE CASCADE;


--
-- Name: user_rewards user_rewards_reward_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: jack
--

ALTER TABLE ONLY public.user_rewards
    ADD CONSTRAINT user_rewards_reward_id_fkey FOREIGN KEY (reward_id) REFERENCES public.rewards(id) ON DELETE CASCADE;


--
-- Name: user_rewards user_rewards_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: jack
--

ALTER TABLE ONLY public.user_rewards
    ADD CONSTRAINT user_rewards_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.users(user_id) ON DELETE CASCADE;


--
-- PostgreSQL database dump complete
--

