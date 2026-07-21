import {SideNav, SideNavItem} from "@vaadin/react-components";
import {useEffect, useMemo, useState} from "react";
import {
    BookmarkEndpoint,
    MenuService,
    ParticipantAdminEndpoint,
    PublicationsEndpoint,
    QuestionEndpoint,
    SettingsEndpoint
} from "Frontend/generated/endpoints";
import Menu from "Frontend/generated/be/riddler/v1/menu/client/model/Menu";
import {useLocation, useParams} from "react-router";
import Participant from "Frontend/generated/be/riddler/v1/participant/client/model/Participant";
import Bookmark from "Frontend/generated/be/riddler/v1/settings/model/Bookmark";
import {Button} from "@vaadin/react-components/Button.js";
import BookmarkType from "Frontend/generated/be/riddler/v1/settings/model/BookmarkType";
import {useSettingsState} from "Frontend/views/secured/settings-context-provider";
import Question from "Frontend/generated/be/riddler/v1/question/client/model/Question";
import {LOGGER, Notify, Objects, Strings, Urls} from "Frontend/util";
// @ts-ignore
import styles from 'Frontend/themes/riddler/common.module.css';
import Publication from "Frontend/generated/be/riddler/v1/publication/client/model/Publication";
import {Navigate} from "Frontend/util/navigate";
import {BookmarkCheck, BookmarkOff} from "lucide-react";
import {Authorisation} from "Frontend/util/authorisation";

export interface Bookmarkable extends Bookmark {
    bookmarkable: boolean;
}

export function SideBar() {
    const [menus, setMenus] = useState<Menu[]>([]);
    const [participant, setParticipant] = useState<Participant>();
    const [question, setQuestion] = useState<Question>();
    const [publication, setPublication] = useState<Publication>();
    const [administration, setAdministration] = useState<string>("");
    const [, setBookmarkTypes] = useState<String[]>([]);
    const {settings, setSettings} = useSettingsState();
    const location = useLocation();
    const params = useParams();

    useEffect(() => {
        LOGGER.debug('useEffect: {} {}', params.id, location.pathname);
        MenuService.menu()
            .then(setMenus)
            .catch(err => Notify.error('Could not fetch the menus {}', err));
        BookmarkEndpoint.bookmarkTypes()
            .then(setBookmarkTypes)
            .catch(err => Notify.error('Could not fetch the bookmark types {}', err));
    }, []);

    useEffect(() => {
        LOGGER.debug('useEffect [params.id, location.pathname]: {} {}', params.id, location.pathname);
        if (isParticipant()) {
            searchParticipant();
        } else {
            setParticipant(undefined);
        }

        if (isQuestionDetail()) {
            searchQuestion();
        } else {
            setQuestion(undefined);
        }

        if (isAdministration()) {
            searchAdministration();
        } else {
            setAdministration(Strings.EMPTY);
        }

        if (isPublication()) {
            searchPublication();
        } else {
            setPublication(undefined);
        }
    }, [params.id, location.pathname]);

    const isParticipant = () => {
        return !!(params.id && location.pathname.startsWith(`/${BookmarkType.PARTICIPANTS.toLocaleLowerCase()}/`));
    };

    const isQuestionDetail = () => {
        return !!(params.id && location.pathname.startsWith(`/${BookmarkType.QUESTIONS.toLocaleLowerCase()}/`));
    };

    const isAdministration = () => {
        return !!(params.id && location.pathname.startsWith(`/${BookmarkType.ADMINISTRATIONS.toLocaleLowerCase()}/`));
    };
    const isPublication = () => {
        return !!(params.id && location.pathname.startsWith(`/${BookmarkType.PUBLICATIONS.toLocaleLowerCase()}/`));
    };

    const searchQuestion = () => {
        LOGGER.debug('Fetching question {}', params.id);
        QuestionEndpoint.get(params.id!!)
            .then(setQuestion)
            .catch(err => Notify.error('Could not fetch the question {}', err));
    };
    const searchPublication = () => {
        LOGGER.debug('Fetching publication {}', params.id);
        PublicationsEndpoint.findPublicationById(params.id!!)
            .then(setPublication)
            .catch(err => Notify.error('Could not find the publication {}', err));
    };
    const searchParticipant = () => {
        LOGGER.debug('Fetching participant {}', params.id);
        ParticipantAdminEndpoint.findById(params.id!!)
            .then(setParticipant)
            .catch(err => Notify.error('Could not find the participant {}', err));
    };

    const addBookmark = (activeChildrenList: Bookmarkable[], bookmarkType: BookmarkType, label: string): Bookmarkable[] => {
        if (params.id && Urls.isSamePath(location.pathname, Urls.makePath(bookmarkType, params.id))) {
            let bookmark: Bookmarkable = {
                bookmark_type: bookmarkType,
                path: Urls.makePath(bookmarkType, params.id!!),
                label: label,
                bookmarkable: true
            };
            let foundBookmark = activeChildrenList.find(e => e.path === bookmark.path);
            if (foundBookmark) {
                bookmark = foundBookmark;
            }
            const tmp = activeChildrenList.filter(e => e.path !== bookmark.path);
            return [bookmark, ...tmp];
        }
        return activeChildrenList;
    };
    const searchAdministration = () => {
        setAdministration('administration');
    }

    const bookmarksJsonString = JSON.stringify((settings.bookmarks || []).map(b => {
        return {
            id: b.id,
            bookmark_type: b.bookmark_type,
            path: b.path,
            label: b.label,
            bookmarkable: true
        } as Bookmarkable;
    }));
    const menusJsonString = JSON.stringify(menus);
    let label = "";

    if (Urls.isSamePath(location.pathname, Urls.makePath(BookmarkType.PARTICIPANTS, params.id))) {
        label = `${participant?.first_name} ${participant?.last_name}`;
    } else if (Urls.isSamePath(location.pathname, Urls.makePath(BookmarkType.QUESTIONS, params.id))) {
        label = question?.title!!;
    } else if (Urls.isSamePath(location.pathname, Urls.makePath(BookmarkType.ADMINISTRATIONS, params.id))) {
        label = administration;
    } else if (Urls.isSamePath(location.pathname, Urls.makePath(BookmarkType.PUBLICATIONS, params.id))) {
        label = publication?.title!!;
    }

    const mappedMenuData = useMemo(() => {
        const parsedMenus: Menu[] = JSON.parse(menusJsonString);
        const parsedBookmarks: Bookmarkable[] = JSON.parse(bookmarksJsonString);

        return parsedMenus.map((menu) => {
            const menuPath = menu.path!!;
            const cleanMenuHash = menuPath.startsWith('/#') ? menuPath.substring(1) : menuPath;
            const routingPath = cleanMenuHash.replace(/^#/, '');
            let activeChildrenList: Bookmarkable[] = parsedBookmarks.filter((b): b is Bookmarkable => {
                return Objects.isNotNullAndNotUndefined(b) && Objects.isEqual(b.bookmark_type, menu.bookmark_type);
            });
            activeChildrenList = addBookmark(activeChildrenList, menu.bookmark_type, label);
            if (menu.bookmark_type === BookmarkType.ADMINISTRATIONS && Authorisation.hasRole([Authorisation.ADMIN])) {
                activeChildrenList.push({
                    path: "/administrations/categories",
                    label: 'Categories',
                    bookmark_type: BookmarkType.ADMINISTRATIONS,
                    bookmarkable: false
                })
            }
            const isCurrent = (location.hash || '#/') === cleanMenuHash;
            const shouldBeExpanded = activeChildrenList.length > 0;
            return {
                menu,
                menuPath,
                routingPath,
                isCurrent,
                shouldBeExpanded,
                activeChildrenList
            };
        });
    }, [menusJsonString, bookmarksJsonString, params.id, location.pathname, location.hash, label]);

    const createBookmark = (bookmark: Bookmarkable) => {
        BookmarkEndpoint.bookmark({
            path: bookmark.path,
            label: bookmark.label,
            bookmark_type: bookmark.bookmark_type
        })
            .then(settings => {
                Notify.success('Bookmark {} added', bookmark.label);
                setSettings(settings);
            })
            .catch(err => Notify.error('Could not find the settings {}', err));
    };

    const deleteBookmark = (bookmark: Bookmarkable) => {
        LOGGER.debug('Bookmark {}', bookmark.id);
        BookmarkEndpoint.deleteBookmark(bookmark.id!!)
            .then(() => {
                Notify.warn('Bookmark {} removed', bookmark.label)
                SettingsEndpoint.getSettings()
                    .then(setSettings)
                    .catch(err => Notify.error('Could not retrieve the settings {}', err));
            })
            .catch(err => Notify.error('Could not delete the bookmark {}', err));
    };

    const isBookmarked = (path: string | undefined): boolean => {
        const currentBookmarks: Bookmarkable[] = JSON.parse(bookmarksJsonString);
        return currentBookmarks.some(b => {
            return Urls.isSamePath(b.path, path);
        });
    };

    return (
        <SideNav id="sideNav">
            {mappedMenuData.map(({menu, menuPath, routingPath, isCurrent, shouldBeExpanded, activeChildrenList}) => (
                <SideNavItem
                    key={menuPath}
                    ref={(el) => {
                        if (el) {
                            if (el) {
                                if (isCurrent) {
                                    el.setAttribute('current', '');
                                } else {
                                    el.removeAttribute('current');
                                }
                                if (shouldBeExpanded) {
                                    el.setAttribute('expanded', '');
                                } else {
                                    el.removeAttribute('expanded');
                                }
                            }
                        }
                    }}
                    onClick={(e) => {
                        e.preventDefault();
                        Navigate.path(routingPath);
                    }}
                >
                    <div style={{
                        display: 'flex',
                        alignItems: 'center',
                        justifyContent: 'space-between',
                        width: '100%',
                        minWidth: 0
                    }}>
                        <span onClick={() => Navigate.path(routingPath)} style={{cursor: 'pointer', flexGrow: 1}}>
                            {menu.label}
                        </span>
                    </div>

                    {activeChildrenList.map(childBookmark => {
                        const isChildCurrent = location.pathname === childBookmark.path;
                        const bookmarked = isBookmarked(childBookmark.path);
                        return (
                            <SideNavItem
                                key={childBookmark.path}
                                slot="children"
                                ref={(el) => {
                                    if (el) {
                                        if (isChildCurrent) {
                                            el.setAttribute('current', '');
                                        } else {
                                            el.removeAttribute('current');
                                        }
                                    }
                                }}
                                onClick={(e) => {
                                    e.stopPropagation();
                                    e.preventDefault();
                                    Navigate.path(childBookmark.path!!);
                                }}
                            >
                                <div style={{
                                    display: 'flex',
                                    alignItems: 'center',
                                    justifyContent: 'space-between',
                                    width: '100%',
                                    minWidth: 0,
                                    gap: 'var(--lumo-space-s)'
                                }}>
                                    <div
                                        className={styles.ellipsis_single}
                                        title={childBookmark.label}
                                        onClick={() => Navigate.path(childBookmark.path!!)}
                                        style={{
                                            cursor: 'pointer',
                                            flexGrow: 1,
                                            fontSize: 'var(--lumo-font-size-s)'
                                        }}
                                    >
                                        {childBookmark.label}
                                    </div>
                                    {childBookmark.bookmarkable && (
                                        <Button
                                            theme="tertiary icon"
                                            style={{
                                                padding: 0,
                                                minWidth: 'var(--lumo-size-s)',
                                                height: 'var(--lumo-size-s)',
                                                color: bookmarked ? 'var(--lumo-primary-text-color)' : 'var(--lumo-disabled-text-color)'
                                            }}
                                            onClick={(e) => {
                                                e.stopPropagation();
                                                bookmarked ? deleteBookmark(childBookmark) : createBookmark(childBookmark);
                                            }}
                                        >
                                            {bookmarked ? (
                                                    <BookmarkCheck size={16}/>
                                                ) :
                                                (
                                                    <BookmarkOff size={16}/>
                                                )}
                                        </Button>
                                    )}
                                </div>
                            </SideNavItem>
                        );
                    })}
                </SideNavItem>
            ))}
        </SideNav>
    );
}
