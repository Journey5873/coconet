import MobileCategoryButton from "../atoms/Button/MobileSelectButton";

export interface CategoryOptionProps {
    children?: React.ReactNode;
    categoryOption: string;
    value: number;
    index: number;
    checked?: boolean;
    handleChangeOption: (option: string) => void;
}

export default function MobileCategoryButtonList(props: CategoryOptionProps) {
    const { categoryOption, handleChangeOption } = props;
    return (
        <>
            <MobileCategoryButton
                categoryOption={"전체"}
                checked={categoryOption === '전체'}
                value={0}
                index={0}
                handleChangeOption={handleChangeOption}
            />
            <MobileCategoryButton
                categoryOption={"프로젝트"}
                checked={categoryOption === '프로젝트'}
                value={0}
                index={0}
                handleChangeOption={handleChangeOption}
            />
            <MobileCategoryButton
                categoryOption={"스터디"}
                checked={categoryOption === '스터디'}
                value={0}
                index={0}
                handleChangeOption={handleChangeOption}
            />
        </>
    )
}