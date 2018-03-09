package com.tiilii.rtc.ui.practise.teacher.list;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.tiilii.rtc.R;
import com.tiilii.rtc.base.BaseFragment;
import com.tiilii.rtc.model.HomeworkModel;
import com.tiilii.rtc.ui.practise.teacher.statistic.HomeworkStatisticActivity;
import com.tiilii.rtc.widget.PoweredRecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 作业列表
 *
 * @author fox
 * @since 2018/01/12
 */

public class HomeworkListFragment extends BaseFragment implements HomeworkListContract.View {

    /**
     * 作业列表数据
     */
    private List<HomeworkModel> mHomeworkModelList = new ArrayList<>();
    /**
     * 作业列表适配器
     */
    private HomeworkListAdapter mHomeworkListAdapter;
    /**
     * 字体
     */
    private Typeface typeface;
    private Map<Integer, String> mCountdown = null;

    @BindView(R.id.rv_homework_list)
    PoweredRecyclerView homeworkListRecyclerView;
    @BindView(R.id.iv_empty)
    ImageView emptyImageView;

    @Inject
    HomeworkListContract.Presenter mPresenter;

    @Inject
    public HomeworkListFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_homework_list, container, false);
        ButterKnife.bind(this, root);

        // 配置作业列表视图数据
        mHomeworkListAdapter = new HomeworkListAdapter();
        homeworkListRecyclerView.getContentRecyclerView().setAdapter(mHomeworkListAdapter);
        homeworkListRecyclerView.setColorSchemeResources(R.color.red_1);
        homeworkListRecyclerView.setOnDataChangeListener(new HomeworkListOnDataChangeListener());

//        typeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/华康翩翩.ttf");
        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        mPresenter.bindView(this);
    }

    @Override
    public void onResume() {
        super.onResume();

//        mPresenter.startCountdown();
        mPresenter.refreshHomeworkList();
    }

    @Override
    public void onPause() {
        super.onPause();

        mPresenter.clearSubscribe();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.unSubscribe();
    }

    @Override
    public void showEmptyView() {

        emptyImageView.setVisibility(View.VISIBLE);
    }

    @Override
    public void updateHomeworkList(List<HomeworkModel> homeworkModelList) {

        int previousSize = mHomeworkModelList.size();
        mHomeworkModelList.addAll(homeworkModelList);
        mHomeworkListAdapter.notifyItemRangeInserted(previousSize, homeworkModelList.size());
    }

    @Override
    public void clearHomeworkList() {

        mHomeworkModelList.clear();
        mHomeworkListAdapter.notifyDataSetChanged();
    }

    @Override
    public void updateAdapter(Map<Integer, String> countdown) {
        mCountdown = countdown;
        mHomeworkListAdapter.notifyDataSetChanged();
    }

    /**
     * 作业列表数据变化事件监听
     */
    private class HomeworkListOnDataChangeListener implements PoweredRecyclerView.OnDataChangeListener {

        @Override
        public void onRefreshData() {

            mPresenter.refreshHomeworkList();
        }

        @Override
        public void onLoadMoreData() {

            mPresenter.loadMoreHomeworkList();
        }

        @Override
        public boolean onLoadStateSet() {

            return mPresenter.hasMoreHomeworkList();
        }
    }

    /**
     * 作业列表适配器
     */
    public class HomeworkListAdapter extends RecyclerView.Adapter<HomeworkListAdapter.HomeworkListHold> {

        @Override
        public HomeworkListHold onCreateViewHolder(ViewGroup parent, int viewType) {
            return new HomeworkListHold(LayoutInflater.from(mContext).inflate(R.layout.item_recycler_homework_list_new, parent, false));
        }

        @Override
        public void onBindViewHolder(HomeworkListHold holder, int position) {
            holder.setPosition(position);

            HomeworkModel homeworkModel = mHomeworkModelList.get(position);

            // 作业名称
//            holder.homeworkNameTextView.setTypeface(typeface);

            // 作业班级月科目信息
            String className = homeworkModel.getClassesName();
            String subjectName = homeworkModel.getCourseName();

            holder.homeworkClassTextView.setText(className);
//            holder.subjectTextView.setText(subjectName);

//            holder.classTextView.setText(className);
//            holder.classTextView.setTypeface(typeface);

            // 发布人
//            String publisherName = homeworkModel.getCreatorName();
//            holder.publisherTextView.setText(publisherName);
//            holder.publisherTextView.setTypeface(typeface);

            // 作业背景
//            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) holder.homeworkBgImageView.getLayoutParams();
//            params.width = ScreenUtils.getScreenWidth(context) - DensityUtils.dpToPx(context, 20);
//            params.height = params.width / 2;
//            holder.homeworkBgImageView.setLayoutParams(params);

            String homeworkTypeId = homeworkModel.getHomeworkTypeId();

            switch (homeworkTypeId) {
                case "1":// 课堂作业
//                    holder.homeworkBgImageView.setImageResource(R.drawable.img_homework_classroom_bg);
//                    holder.homeworkNameTextView.setText("课堂作业");
                    holder.titleTextView.setText("课堂作业——" + subjectName);
                    break;
                case "2":// 家庭作业
//                    holder.homeworkBgImageView.setImageResource(R.drawable.img_homework_home_bg);
//                    holder.homeworkNameTextView.setText("家庭作业");
                    holder.titleTextView.setText("家庭作业——" + subjectName);
                    break;
                case "3":// 假期作业
//                    holder.homeworkBgImageView.setImageResource(R.drawable.img_homework_holiday_bg);
//                    holder.homeworkNameTextView.setText("假期作业");
                    holder.titleTextView.setText("假期作业——" + subjectName);
                    break;
                default:
                    break;
            }

            // 设置作业状态
            int homeworkStatus = homeworkModel.getHomeworkStatus();
            // 作业开始时间与结束时间
            String start_time = homeworkModel.getHomeworkBeginDatetime().substring(0, 16);
            String end_time = homeworkModel.getHomeworkEndDatetime().substring(0, 16);

            switch (homeworkStatus) {
                case 0:// 未开始
//                    holder.homeworkStatusImageView.setImageResource(R.drawable.img_homework_not_start);
                    if (mCountdown != null && mCountdown.containsKey(position)) {
//                        holder.startAndEndTextView.setText("开始倒计时" + mCountdown.get(position));

                        String[] duration = mCountdown.get(position).split("@");

                        holder.durationTextView.setText(duration[0]);
                        holder.durationSuffixTextView.setVisibility(View.VISIBLE);
                        holder.durationSuffixTextView.setText(duration[1] + "开始");
                    }
                    break;
                case 1:// 进行中
//                    holder.homeworkStatusImageView.setImageResource(R.drawable.img_homework_working);
                    if (mCountdown != null && mCountdown.containsKey(position)) {
//                        holder.startAndEndTextView.setText("结束倒计时" + mCountdown.get(position));

                        String[] duration = mCountdown.get(position).split("@");

                        holder.durationTextView.setText(duration[0]);
                        holder.durationSuffixTextView.setVisibility(View.VISIBLE);
                        holder.durationSuffixTextView.setText(duration[1] + "结束");
                    }
                    break;
                case 2:// 已完成
//                    holder.homeworkStatusImageView.setImageResource(R.drawable.img_homework_complete);
//                    holder.startAndEndTextView.setText(start_time + " ～ " + end_time);

                    holder.durationTextView.setText("已结束");
                    holder.durationSuffixTextView.setVisibility(View.GONE);
                    break;
                case 3:// 未完成
//                    holder.homeworkStatusImageView.setImageResource(R.drawable.img_homework_not_complete);
//                    holder.startAndEndTextView.setText(start_time + " ～ " + end_time);

                    holder.durationTextView.setText("已结束");
                    holder.durationSuffixTextView.setVisibility(View.GONE);
                    break;
                default:
                    break;
            }
        }

        @Override
        public int getItemCount() {
            return mHomeworkModelList.size();
        }

        public class HomeworkListHold extends RecyclerView.ViewHolder {

//            /**
//             * 作业类型
//             */
//            @Nullable
//            @BindView(R.id.tv_homework_name)
//            TextView homeworkNameTextView;
            /**
             * 作业标题
             */
            @BindView(R.id.tv_homework_title)
            TextView titleTextView;
            /**
             * 作业班级
             */
            @BindView(R.id.tv_homework_class)
            TextView homeworkClassTextView;
            /**
             * 作业剩余时间
             */
            @BindView(R.id.tv_homework_duration)
            TextView durationTextView;
            /**
             * 作业剩余时间描述
             */
            @BindView(R.id.tv_homework_duration_suffix)
            TextView durationSuffixTextView;
            /**
             * 科目信息
             */
            @Nullable
            @BindView(R.id.tv_subject)
            TextView subjectTextView;
//            /**
//             * 班级信息
//             */
//            @Nullable
//            @BindView(R.id.tv_class)
//            TextView classTextView;
//
//            /**
//             * 作业开始时间与结束时间
//             */
//            @Nullable
//            @BindView(R.id.tv_start_and_end)
//            TextView startAndEndTextView;
//
//            /**
//             * 作业背景图片
//             */
//            @Nullable
//            @BindView(R.id.iv_homework_bg)
//            ImageView homeworkBgImageView;
//
//            /**
//             * 作业状态
//             */
//            @Nullable
//            @BindView(R.id.iv_status)
//            ImageView homeworkStatusImageView;
//            /**
//             * 发布人
//             */
//            @Nullable
//            @BindView(R.id.tv_publisher)
//            TextView publisherTextView;

            private int position;

            public HomeworkListHold(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }

            public void setPosition(int position) {
                this.position = position;
            }

            @OnClick(R.id.fl_homework)
            void showHomeworkSchedule() {

                HomeworkModel homeworkModel = mHomeworkModelList.get(position);
                if (homeworkModel.getHomeworkStatus() == 0) {
                    ToastUtils.showShort("作业未开始");
                    return;
                }

                Intent intent = new Intent(mContext, HomeworkStatisticActivity.class);
                intent.putExtra(HomeworkStatisticActivity.MY_HOMEWORK_UID, String.valueOf(homeworkModel.getMyHomeworkUid()));
                intent.putExtra(HomeworkStatisticActivity.HOMEWORK_UID, String.valueOf(homeworkModel.getHomeworkUid()));
                intent.putExtra(HomeworkStatisticActivity.COURSE_NAME, homeworkModel.getCourseName());
                startActivity(intent);
            }
        }
    }
}
